package ru.practicum.android.diploma.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.Debounce
import ru.practicum.android.diploma.util.INTERNET_ERROR

class SearchViewModel(private val debounce: Debounce, private val searchInteractor: SearchInteractor) : ViewModel() {

    private var searchText: String? = null
    private var searchState = MutableLiveData<SearchState>()
    val trackListLiveData: LiveData<SearchState> = searchState
    private var currentPage: Int = 0
    private var maxPages: Int = 0
    private var vacanciesList = mutableListOf<DomainVacancy>()
    private var isNextPageLoading: Boolean = false

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    fun searchDebounce(text: String) {
        if (text.isEmpty() || text == searchText) {
            debounce.cancel()
        } else {
            currentPage = 0
            vacanciesList.clear()
            val debouncedFunction = debounce.debounceFunction<String>(SEARCH_DEBOUNCE_DELAY) { searchText ->
                searchVacancy(searchText)
            }
            debouncedFunction(text)
        }
    }

    fun clearSearchResults() {
        searchState.postValue(SearchState.Default)
    }

    private fun searchVacancy(text: String) {
        searchText = text
        if (currentPage > 0) {
            searchState.postValue(SearchState.Continuation)
        } else {
            searchState.postValue(SearchState.Loading)
        }

        viewModelScope.launch {
            searchInteractor
                .searchVacancies(text, currentPage)
                .collect { pair -> processResult(pair.first, pair.second) }
            maxPages = searchInteractor.pages?: 0
        }
    }

    private fun processResult(vacancies: List<DomainVacancy>?, errorCode: Int?) {
        if (vacancies != null) {
            if (vacancies.isEmpty()) {
                searchState.postValue(SearchState.NoResults)
            } else {
                vacanciesList.addAll(vacancies)
                searchState.postValue(SearchState.Success(vacanciesList, searchInteractor.foundItems!!))
            }
        } else if (errorCode != null) {
            Log.i("123", errorCode.toString())
            when (errorCode) {
                INTERNET_ERROR -> {
                    searchState.postValue(SearchState.NoInternet)
                } else -> {
                    searchState.postValue(SearchState.Error(R.string.server_error.toString()))
                }
            }
        }
        isNextPageLoading = false
    }

    fun onLastItemReached() {
        if (!isNextPageLoading) {
            currentPage++
            if (maxPages >= currentPage + 1) {
                isNextPageLoading = true
                searchText?.let { searchVacancy(it) }
            }
        }
    }
}
