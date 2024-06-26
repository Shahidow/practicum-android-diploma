package ru.practicum.android.diploma.domain.filtration.models

data class FilterParams(
    val areaDomain: AreaDomain,
    val filterRegionArea: FilterRegionArea,
    val industryDomain: IndustryDomain,
    val salary: Int,
    val showOnlyWithSalary: Boolean
)
