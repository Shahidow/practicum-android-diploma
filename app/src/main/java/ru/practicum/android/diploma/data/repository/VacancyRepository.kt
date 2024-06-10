package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.network.HeadHunterApi

class VacancyRepository(private val api: HeadHunterApi) {

    private val token = "Bearer TOKEN"

    suspend fun getVacancies(filters: Map<String, String>): NetworkResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVacancies(token, filters)
                if (response.isSuccessful) {
                    NetworkResponse(response.body()?.items ?: emptyList(), 200)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(emptyList(), 400, errorBody)
                }
            } catch (ex: Exception) {
                NetworkResponse(emptyList(), 400, ex.message)
            }
        }
    }

    suspend fun getVacancyDetails(id: String): NetworkResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVacancyDetails(token, id)
                if (response.isSuccessful) {
                    NetworkResponse(response.body() ?: throw NotFoundException("Vacancy not found"), 200)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(null, 400, errorBody)
                }
            } catch (ex: Exception) {
                NetworkResponse(null, 400, ex.message)
            }
        }
    }
}

data class NetworkResponse(
    val data: Any?,
    val resultCode: Int,
    val errorMessage: String? = null
)
