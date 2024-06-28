package ru.practicum.android.diploma.data.dto

data class IndustryResponse(
    val id: String,
    val name: String,
    val industries: List<SubIndustry>
)

data class SubIndustry(
    val id: String,
    val name: String
)
