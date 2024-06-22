package ru.practicum.android.diploma.util

sealed class Resource<T>(val data: T? = null, val resultCode: Int? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(resultCode: Int) : Resource<T>(resultCode = resultCode)
}
