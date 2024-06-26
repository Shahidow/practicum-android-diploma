package ru.practicum.android.diploma.domain.filtration.models

data class AreaDomain(
    val id: String,
    val name: String,
    val parentId: String?,
    val areas: List<AreaDomain>
)
