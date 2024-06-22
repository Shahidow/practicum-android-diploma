package ru.practicum.android.diploma.data.dto

data class NetworkResponse<T>(
    val data: T?,
    val resultCode: Int,
    val errorMessage: String? = null
)
