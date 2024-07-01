package ru.practicum.android.diploma.presentation.filtration.place.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtration.FiltrationInteractor
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.SERVER_ERROR

class FilterCountryViewModel(private val interactor: FiltrationInteractor) : ViewModel() {

    private val tag = "filter"
    private val _filtrationParams = MutableLiveData<FilterCountryViewState>()
    val filtrationParams: LiveData<FilterCountryViewState> get() = _filtrationParams

    fun getCountryList() {
        viewModelScope.launch {
            val result = interactor.getAreas()
            if (result.data != null) {
                _filtrationParams.postValue(FilterCountryViewState.CountryList(result.data))
            } else if (result.resultCode != null) {
                when (result.resultCode) {
                    SERVER_ERROR -> {
                        Log.e(tag, "server error")
                        _filtrationParams.postValue(FilterCountryViewState.EmptyCountryList)
                    }

                    INTERNET_ERROR -> {
                        Log.e(tag, "enternet error")
                        _filtrationParams.postValue(FilterCountryViewState.NoInternetConnection)
                    }
                }
            }
        }
    }

}
