package ru.practicum.android.diploma.domain.filtration

import ru.practicum.android.diploma.domain.filtration.models.FilterParams

interface FiltrationParamsSaveInteractor {
    fun getFilterParams(): FilterParams?
    fun saveFilterParams(filterParams: FilterParams)
    fun removeFilterParams()
}
