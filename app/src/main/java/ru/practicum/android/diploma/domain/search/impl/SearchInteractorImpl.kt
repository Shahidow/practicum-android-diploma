package ru.practicum.android.diploma.domain.search.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.domain.filtration.models.FilterParams
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
        filters: FilterParams?,
    ): Flow<Pair<List<DomainVacancy>?, Int?>> {
        val filtersMap = filtersToMap(filters)
        return repository.searchVacancies(text, page, filtersMap).map { result ->
            when (result) {
                is Resource.Success -> {
                    currentPage = repository.currentPage
                    foundItems = repository.foundItems
                    pages = repository.pages
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.resultCode)
                }
            }
        }
    }

    private fun filtersToMap(filters: FilterParams?): Map<String, String>? {
        if (filters != null) {
            val options: HashMap<String, String> = HashMap()
            if (filters.region != null) {
                options["area"] = filters.region.id
            } else if (filters.country != null) {
                options["area"] = filters.country.id
            }
            if (filters.industry != null) {
                options["industry"] = filters.industry.id
            }
            if (filters.salary != null) {
                options["salary"] = filters.salary.toString()
            }
            options["only_with_salary"] = filters.showOnlyWithSalary.toString()
            return options
        } else {
            return null
        }
    }
}
