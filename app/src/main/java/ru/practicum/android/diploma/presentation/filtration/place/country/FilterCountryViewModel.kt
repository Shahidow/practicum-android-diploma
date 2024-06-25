package ru.practicum.android.diploma.presentation.filtration.place.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilterCountryViewModel : ViewModel() {
    // (private val filtrationParamsInteractor:FiltrationParamsInteractor)
    private val tag: String = "filter_country"
    private val _filtrationParams = MutableLiveData<FilterCountryViewState>()
    val filtrationParams: LiveData<FilterCountryViewState> get() = _filtrationParams

    /* fun getCountryList() {
         viewModelScope.launch {
             filtrationParamsInteractor.getCountrysList().collect {
                 try {
                     if (countrysList?.isEmpty() == true) {
                         _filtrationParams.postValue(
                             FilterCountryViewState.EmptyCountryList
                         )
                     } else {
                         _filtrationParams.postValue(
                             countrysList?.let { list ->
                                 FilterCountryViewState.CountryList(countryList = list)
                             }
                         )
                     }
                 } catch (e: IOException) {
                     Log.e(tag, "Caught exception:  ${e.message}")
                     _filtrationParams.postValue(
                         FilterCountryViewState.EmptyCountryList
                     )
                 }
             }
         }
     }
     */
}
