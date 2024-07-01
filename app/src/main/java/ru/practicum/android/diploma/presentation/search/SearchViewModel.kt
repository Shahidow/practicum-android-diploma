package ru.practicum.android.diploma.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.Debounce
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.IsLastPage

class SearchViewModel(
    private val debounce: Debounce,
    private val searchInteractor: SearchInteractor,
    private val filtersInteractor: FiltrationParamsSaveInteractor,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var searchText: String? = savedStateHandle.get<String>("searchText")
    private var searchState = MutableLiveData<SearchState>()
    val trackListLiveData: LiveData<SearchState> = searchState
    private var currentPage: Int = savedStateHandle.get<Int>("currentPage") ?: 0
    private var maxPages: Int = savedStateHandle.get<Int>("maxPages") ?: 0
    private var vacanciesList: MutableList<DomainVacancy> = savedStateHandle["vacanciesList"] ?: mutableListOf()
    private var isNextPageLoading: Boolean = false
    private var filterButtonHighlighted = MutableLiveData<Boolean>()
    val filterButtonHighlight: LiveData<Boolean> = filterButtonHighlighted

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    init {
        checkActiveFilters()
        if (vacanciesList.isNotEmpty()) {
            searchState.postValue(SearchState.Success(vacanciesList, searchInteractor.foundItems ?: 0))
        } else if (!searchText.isNullOrEmpty()) {
            searchVacancy(searchText!!)
        }
    }

    private fun checkActiveFilters() {
        filterButtonHighlighted.postValue(filtersInteractor.hasActiveFilters())
    }

    private fun saveState() {
        savedStateHandle["searchText"] = searchText
        savedStateHandle["currentPage"] = currentPage
        savedStateHandle["maxPages"] = maxPages
        savedStateHandle["vacanciesList"] = vacanciesList
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
                .searchVacancies(text, currentPage, filtersInteractor.getFilterParams())
                .collect { pair -> processResult(pair.first, pair.second) }
            maxPages = searchInteractor.pages ?: 0
            saveState()
        }
    }

    private fun processResult(vacancies: List<DomainVacancy>?, errorCode: Int?) {
        if (vacancies != null) {
            if (vacancies.isEmpty()) {
                searchState.postValue(SearchState.NoResults)
            } else {
                vacanciesList.addAll(vacancies)
                searchState.postValue(SearchState.Success(vacanciesList, searchInteractor.foundItems!!))
                saveState()
            }
        } else if (errorCode != null) {
            when (errorCode) {
                INTERNET_ERROR -> {
                    searchState.postValue(SearchState.NoInternet)
                }

                else -> {
                    searchState.postValue(SearchState.Error(R.string.server_error.toString()))
                }
            }
        }
        isNextPageLoading = false
    }

    fun onLastItemReached() {
        if (!isNextPageLoading) {
            currentPage++
            IsLastPage.IS_LAST_PAGE = currentPage == maxPages - 1
            if (maxPages > currentPage) {
                isNextPageLoading = true
                searchText?.let { searchVacancy(it) }
            }
        }
    }

    fun onResume() {
      checkActiveFilters()
        if (vacanciesList.isEmpty()) {
            currentPage = 0
            searchText?.let { searchVacancy(it) }
        } else {
            searchState.postValue(SearchState.Success(vacanciesList, searchInteractor.foundItems!!))
        }
    }
}
