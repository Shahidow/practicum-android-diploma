package ru.practicum.android.diploma.presentation.search

import android.content.SharedPreferences
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
import ru.practicum.android.diploma.util.IsLastPage

class SearchViewModel(
    private val debounce: Debounce,
    private val searchInteractor: SearchInteractor,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var searchText: String? = null
    private var searchState = MutableLiveData<SearchState>()
    val trackListLiveData: LiveData<SearchState> = searchState
    private var currentPage: Int = 0
    private var maxPages: Int = 0
    private var vacanciesList = mutableListOf<DomainVacancy>()
    private var isNextPageLoading: Boolean = false
    private var filterParameters: MutableMap<String, String> = mutableMapOf()

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    init {
        // Загрузка фильтров при инициализации ViewModel
        loadFilterParameters()
    }

    // Функция для загрузки фильтров
    private fun loadFilterParameters() {
        val filterParams = mutableMapOf<String, String>()
        sharedPreferences.getString("area", null)?.let { filterParams["area"] = it }
        sharedPreferences.getString("industry", null)?.let { filterParams["industry"] = it }
        sharedPreferences.getString("salary", null)?.let { filterParams["salary"] = it }
        if (sharedPreferences.getBoolean("only_with_salary", false)) {
            filterParams["only_with_salary"] = "true"
        }
        filterParameters.putAll(filterParams)

        // Логируем установку параметров
        Log.d("!!!", "SearchViewModel - Параметры фильтра установлены при инициализации: $filterParams")

        // Проводим поиск с загруженными фильтрами
        searchText?.let { searchVacancy(it) }
    }

    // Функция для установки фильтров
    fun setFilterParameters(filterParams: Map<String, String>) {
        filterParameters.clear()
        filterParameters.putAll(filterParams)

        // Сброс текущей страницы и очистка списка вакансий
        resetPage()

        // Логируем установку параметров
        Log.d("!!!", "SearchViewModel - Параметры фильтра установлены: $filterParams")

        // Проводим поиск с новыми фильтрами
        searchText?.let { searchVacancy(it) }
    }

    // Функция для сброса фильтров
//    fun clearFilters() {
//        sharedPreferences.edit().clear().apply()
//        filterParameters.clear()
//
//        // Сброс текущей страницы и очистка списка вакансий
//        resetPage()
//
//        // Логируем сброс параметров
//        Log.d("!!!", "SearchViewModel - Параметры фильтра сброшены")
//
//        // Проводим поиск с новыми фильтрами
//        searchText?.let { searchVacancy(it) }
//    }

    fun searchDebounce(text: String) {
        if (text.isEmpty() || text == searchText) {
            debounce.cancel()
        } else {
            resetPage()
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
        Log.d("!!!", "SearchViewModel - Выполняется поиск с фильтрами: $filterParameters")

        viewModelScope.launch {
            searchInteractor
                .searchVacancies(text, currentPage, filterParameters)
                .collect { pair -> processResult(pair.first, pair.second) }
            maxPages = searchInteractor.pages ?: 0
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
        IsLastPage.IS_LAST_PAGE = true
    }

    private fun resetPage() {
        currentPage = 0
        vacanciesList.clear()
    }
}
