package ru.practicum.android.diploma.data.filtration

interface FiltrationParamsSaveRepository {
    fun getFilterParams(): List<String>
    fun saveFilterParams(listFilterParams: List<String>)
    fun insertFilterParam(filterParam: String)
}
