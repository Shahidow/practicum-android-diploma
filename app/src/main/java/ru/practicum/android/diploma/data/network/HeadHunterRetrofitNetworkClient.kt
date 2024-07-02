package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.models.Areas
import ru.practicum.android.diploma.data.dto.models.Industry
import ru.practicum.android.diploma.data.dto.models.VacancyDetails

class HeadHunterRetrofitNetworkClient(private val api: HeadHunterApi) : HeadHunterNetworkClient {

    override suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse> {
        return api.getVacancies(filters)
    }

    override suspend fun getVacancy(id: String): Response<VacancyDetails> {
        return api.getVacancyDetails(id)
    }

    override suspend fun getIndustries(): Response<List<Industry>> {
        return api.getIndustries()
    }

    override suspend fun getAreas(): Response<List<Areas>> {
        return api.getAreas()
    }
}
