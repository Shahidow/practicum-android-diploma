package ru.practicum.android.diploma.data.network

import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.models.AreasList
import ru.practicum.android.diploma.data.dto.models.Industry
import ru.practicum.android.diploma.data.dto.models.VacancyDetails

class HeadHunterRetrofitNetworkClient(private val api: HeadHunterApi) : HeadHunterNetworkClient {

    override suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse> {
        return api.getVacancies(filters)
    }

    override suspend fun getVacancy(id: String): Response<VacancyDetails> {
        return api.getVacancyDetails(id)
    }

    override suspend fun getIndustries(): Response<Industry> {
        return api.getIndustries()
    }

    override suspend fun getAreas(): Response<AreasList> {
        return api.getAreas()
    }
}
