package ru.practicum.android.diploma.domain.filtration.models

data class FilterParams(
    val country: AreaDomain?,
    val region: AreaDomain?,
    val industry: IndustryDomain?,
    val salary: Int?,
    val showOnlyWithSalary: Boolean = false
)
