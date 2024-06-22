package ru.practicum.android.diploma.data.dto

data class VacancyResponse(
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val found: Int
)
