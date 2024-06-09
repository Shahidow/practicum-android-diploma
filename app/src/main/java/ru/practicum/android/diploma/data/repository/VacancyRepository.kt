package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.network.HeadHunterApi

class VacancyRepository(private val api: HeadHunterApi) {

    private val token = "Bearer TOKEN"

    suspend fun getVacancies(filters: Map<String, String>): List<Vacancy> {
        val response = api.getVacancies(token, filters)
        if (response.isSuccessful) {
            return response.body()?.items ?: emptyList()
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Failed to load vacancies: $errorBody")
        }
    }

    suspend fun getVacancyDetails(id: String): VacancyDetails {
        val response = api.getVacancyDetails(token, id)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Vacancy not found")
        } else {
            val errorBody = response.errorBody()?.string() ?: "Unknown error"
            throw Exception("Failed to load vacancy details: $errorBody")
        }
    }
}
