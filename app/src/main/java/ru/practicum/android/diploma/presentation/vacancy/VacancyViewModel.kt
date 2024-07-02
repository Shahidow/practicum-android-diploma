package ru.practicum.android.diploma.presentation.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.favorites.VacancyViewState
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.domain.vacancy.GetVacancyDetailsInteractor
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.SERVER_ERROR

class VacancyViewModel(
    private val getVacancyDetailsInteractor: GetVacancyDetailsInteractor,
    private val favoritesVacancyInteractor: FavoritesVacancyInteractor,
) : ViewModel() {

    private val _vacancyScreenState = MutableLiveData<VacancyViewState>()
    val vacancyScreenState: LiveData<VacancyViewState> get() = _vacancyScreenState

    var currentDomainVacancy: DomainVacancy? = null
        private set

    fun loadVacancyDetails(vacancyId: String) {
        _vacancyScreenState.postValue(VacancyViewState.VacancyLoading)
        viewModelScope.launch {
            val result = getVacancyDetailsInteractor.execute(vacancyId)
            if (result.data != null) {
                currentDomainVacancy = result.data
                _vacancyScreenState.postValue(VacancyViewState.VacancyDataDetail(result.data))
                getFavoriteIds()
            } else {
                when (result.resultCode) {
                    INTERNET_ERROR -> getVacancyFromDataBase(vacancyId)
                    SERVER_ERROR -> _vacancyScreenState.postValue(VacancyViewState.Error)
                }
            }
        }
    }

    fun insertFavoriteVacancy() {
        if (currentDomainVacancy != null) {
            viewModelScope.launch {
                favoritesVacancyInteractor.insertFavoriteVacancy(currentDomainVacancy!!)
                getFavoriteIds()
            }
        }
    }

    fun deleteFavoriteVacancy() {
        if (currentDomainVacancy != null) {
            viewModelScope.launch {
                favoritesVacancyInteractor.deleteFavoriteVacancy(currentDomainVacancy!!)
                getFavoriteIds()
            }
        }
    }

    private fun getFavoriteIds() {
        viewModelScope.launch {
            val favoriteIdList = favoritesVacancyInteractor.getFavoriteIds()
            if (favoriteIdList.contains(currentDomainVacancy?.vacancyId)) {
                _vacancyScreenState.postValue(VacancyViewState.VacancyIsFavorite)
            } else {
                _vacancyScreenState.postValue(VacancyViewState.VacancyIsNotFavorite)
            }
        }
    }

    private fun getVacancyFromDataBase(vacancyId: String) {
        viewModelScope.launch {
            val vacancy = favoritesVacancyInteractor.getOneFavoriteVacancy(vacancyId)
            currentDomainVacancy = vacancy
            _vacancyScreenState.postValue(VacancyViewState.VacancyDataDetail(vacancy))
            getFavoriteIds()
        }
    }
}
