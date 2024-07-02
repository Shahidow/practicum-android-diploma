package ru.practicum.android.diploma.data.filtration

import ru.practicum.android.diploma.domain.filtration.models.FilterParams

interface FiltrationParamsSaveRepository {
    fun getFilterParams(): FilterParams?
    fun saveFilterParams(filterParams: FilterParams)
    fun removeFilterParams()
    fun hasActiveFilters(): Boolean
}
