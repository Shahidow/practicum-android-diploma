package ru.practicum.android.diploma.domain.search.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(private val repository: SearchRepository) : SearchInteractor {
    override var currentPage: Int? = null
    override var foundItems: Int? = null
    override var pages: Int? = null
    override fun searchVacancies(
        text: String,
        page: Int,
        filterParameters: Map<String, String>?
    ): Flow<Pair<List<DomainVacancy>?, Int?>> {
        Log.d("!!!", "SearchInteractorImpl - Начало поиска с текстом: $text, страница: $page, фильтры: $filterParameters")
        return repository.searchVacancies(text, page, filterParameters).map { result ->
            when (result) {
                is Resource.Success -> {
                    currentPage = repository.currentPage ?: 0
                    foundItems = repository.foundItems
                    pages = repository.pages

                    Log.d("!!!", "SearchInteractorImpl - Поиск успешен, найдено ${result.data?.size} items")

                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Log.d("!!!", "SearchInteractorImpl -  Ошибка поиска с кодом: ${result.resultCode}")

                    Pair(null, result.resultCode)
                }
            }
        }
    }
}
