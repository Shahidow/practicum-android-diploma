package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

interface SearchInteractor {
    var currentPage: Int?
    var foundItems: Int?
    var pages: Int?
    fun searchVacancies(text: String, page: Int): Flow<Pair<List<DomainVacancy>?, Int?>>
}
