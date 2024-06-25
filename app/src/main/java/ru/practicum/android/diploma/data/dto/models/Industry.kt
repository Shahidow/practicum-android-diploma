package ru.practicum.android.diploma.data.dto.models

data class Industry(
    val id: String,
    val name: String,
    val industries: List<IndustryItem>,
)

data class IndustryItem(
    val id: String,
    val name: String,
)
