package ru.practicum.android.diploma.data.network

import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.dto.VacancyResponse
import java.io.IOException

class HeadHunterRetrofitNetworkClient(private val api: HeadHunterApi) : HeadHunterNetworkClient {

    companion object {
        private const val AUTHORIZATION_HEADER = "Bearer YOUR_ACCESS_TOKEN"
        private const val ERROR_CODE = 400
    }

    private fun String.toResponseBody(): ResponseBody {
        return this.toResponseBody(null)
    }

    override suspend fun getVacancies(filters: Map<String, String>): Response<VacancyResponse> {
        return try {
            val response = api.getVacancies(AUTHORIZATION_HEADER, filters)
            response
        } catch (e: IOException) {
            Response.error(ERROR_CODE, e.message?.toResponseBody() ?: "".toResponseBody())
        }
    }

    override suspend fun getVacancy(id: String): Response<VacancyDetails> {
        return try {
            val response = api.getVacancyDetails(AUTHORIZATION_HEADER, id)
            response
        } catch (e: IOException) {
            Response.error(ERROR_CODE, e.message?.toResponseBody() ?: "".toResponseBody())
        }
    }
}
