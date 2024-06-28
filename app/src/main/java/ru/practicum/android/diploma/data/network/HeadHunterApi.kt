package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.dto.VacancyResponse
import ru.practicum.android.diploma.data.dto.models.Areas
import ru.practicum.android.diploma.data.dto.models.Industry
import ru.practicum.android.diploma.data.dto.models.VacancyDetails

interface HeadHunterApi {
    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}", "User-Agent: DreamJob (shahidow@mail.com)")
    @GET("vacancies")
    suspend fun getVacancies(
        @QueryMap filters: Map<String, String>,
    ): Response<VacancyResponse>

    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}", "User-Agent: DreamJob (shahidow@mail.com)")
    @GET("vacancies/{id}")
    suspend fun getVacancyDetails(
        @Path("id") id: String,
    ): Response<VacancyDetails>

    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}", "User-Agent: DreamJob (shahidow@mail.com)")
    @GET("industries")
    suspend fun getIndustries(): Response<List<Industry>>

    @Headers("Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}", "User-Agent: DreamJob (shahidow@mail.com)")
    @GET("areas")
    suspend fun getAreas(): Response<List<Areas>>
}
