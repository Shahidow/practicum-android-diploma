package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.models.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.models.Areas
import ru.practicum.android.diploma.data.dto.models.Industry

interface HeadHunterNetworkClient {
    suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse>
    suspend fun getVacancy(id: String): Response<VacancyDetails>
    suspend fun getIndustries(): Response<List<Industry>>
    suspend fun getAreas(): Response<List<Areas>>
}
