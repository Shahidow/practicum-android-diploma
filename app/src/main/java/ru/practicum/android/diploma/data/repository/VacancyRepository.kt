package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.network.HeadHunterApi
import java.io.IOException

class VacancyRepository(private val api: HeadHunterApi) {

    suspend fun getVacancies(filters: Map<String, String>): NetworkResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVacancies(TOKEN, filters)
                if (response.isSuccessful) {
                    NetworkResponse(response.body()?.items ?: emptyList(), SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(emptyList(), ERROR_CODE, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(emptyList(), ERROR_CODE, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(emptyList(), ERROR_CODE, "HTTP error: ${ex.message}")
            }
        }
    }

    suspend fun getVacancyDetails(id: String): NetworkResponse {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getVacancyDetails(TOKEN, id)
                if (response.isSuccessful) {
                    NetworkResponse(response.body() ?: throw NotFoundException("Vacancy not found"), SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(null, ERROR_CODE, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(null, ERROR_CODE, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(null, ERROR_CODE, "HTTP error: ${ex.message}")
            }
        }
    }

    companion object {
        private const val TOKEN = "Bearer TOKEN"
        private const val SUCCESS_CODE = 200
        private const val ERROR_CODE = 400
    }
}

data class NetworkResponse(
    val data: Any?,
    val resultCode: Int,
    val errorMessage: String? = null
)

class NotFoundException(message: String) : Exception(message)
