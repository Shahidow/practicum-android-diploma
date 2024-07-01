package ru.practicum.android.diploma.presentation.filtration.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.util.Debounce
import ru.practicum.android.diploma.util.Resource
import java.net.HttpURLConnection

class IndustryViewModel(
    private val debounce: Debounce,
    private val industryInteractor: FiltrationInteractor,
    private val filtrationParamsSaveInteractor: FiltrationParamsSaveInteractor
) : ViewModel() {

    private var foundIndustries: MutableList<IndustryDomain>? = null

    private val _stateLiveData = MutableLiveData<FiltrationIndustryState>()
    fun getState(): LiveData<FiltrationIndustryState> = _stateLiveData

    private fun filterIndustries(s: String) {
        _stateLiveData.postValue(FiltrationIndustryState.Loading)
        viewModelScope.launch {
            val filteredIndustry = foundIndustries?.filter { it.name.contains(s, ignoreCase = true) }
            if (filteredIndustry.isNullOrEmpty()) {
                _stateLiveData.postValue(FiltrationIndustryState.Empty)
            } else {
                _stateLiveData.postValue(FiltrationIndustryState.Success(filteredIndustry))
            }
        }
    }

    fun selectIndustry() {
        _stateLiveData.postValue(FiltrationIndustryState.Selected)
    }

    fun searchDebounce(text: String) {
        if (text.isEmpty()) {
            debounce.cancel()
        } else {
            val debouncedFunction = debounce.debounceFunction<String>(SEARCH_DEBOUNCE_DELAY) { searchText ->
                filterIndustries(searchText)
            }
            debouncedFunction(text)
        }
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
            data.resultCode == -1 -> {
                _stateLiveData.postValue(FiltrationIndustryState.InternetConnectionError)
            }

            data.resultCode == HttpURLConnection.HTTP_BAD_REQUEST -> {
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

    fun saveIndustryFilter(selectedIndustry: IndustryDomain) {
        // Unfinished
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 500L
    }
}
