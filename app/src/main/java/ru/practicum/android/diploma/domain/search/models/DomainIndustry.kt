package ru.practicum.android.diploma.domain.search.models


data class Industry(
    val id: String,
    val name: String
)


data class IndustryGroup(
    val id: String,
    val name: String,
    val industries: List<Industry>
)
