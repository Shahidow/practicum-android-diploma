package ru.practicum.android.diploma.presentation.filtration.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.util.Debounce
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR

class IndustryViewModel(
    private val industryInteractor: FiltrationInteractor,
) : ViewModel() {

    private var foundIndustries: MutableList<IndustryDomain>? = null
    private val _stateLiveData = MutableLiveData<FiltrationIndustryState>()
    fun getState(): LiveData<FiltrationIndustryState> = _stateLiveData

    fun filterIndustries(s: String) {
        val filteredIndustry = foundIndustries?.filter { it.name.contains(s, ignoreCase = true) }
        if (filteredIndustry.isNullOrEmpty()) {
            _stateLiveData.postValue(FiltrationIndustryState.Empty)
        } else {
            _stateLiveData.postValue(FiltrationIndustryState.Success(filteredIndustry))
        }
    }

    fun selectIndustry() {
        _stateLiveData.postValue(FiltrationIndustryState.Selected)
    }

    fun getIndustries() {
        _stateLiveData.postValue(FiltrationIndustryState.Loading)
        viewModelScope.launch {
            val result = industryInteractor.getIndustries()
            processData(result)
        }
    }

    private fun processData(data: Resource<List<IndustryDomain>>) {
        val industryList = mutableListOf<IndustryDomain>()
        industryList.clear()
        data.data?.let { industryList.addAll(it) }
        when {
            data.resultCode == INTERNET_ERROR -> {
                _stateLiveData.postValue(FiltrationIndustryState.InternetConnectionError)
            }

            data.resultCode == SERVER_ERROR -> {
                _stateLiveData.postValue(FiltrationIndustryState.ServerError)
            }

            industryList.isEmpty() -> {
                _stateLiveData.postValue(FiltrationIndustryState.Empty)
            }

            else -> {
                foundIndustries = industryList
                _stateLiveData.postValue(FiltrationIndustryState.Success(industryList))
            }
        }
    }
}
