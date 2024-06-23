package ru.practicum.android.diploma.domain.filtration.api

interface FiltrationParamsSaveInteractor {
    fun getFilterParams(): List<String>
    fun saveFilterParams(listFilterParams: List<String>)
}
