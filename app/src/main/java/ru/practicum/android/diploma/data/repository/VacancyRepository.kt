package ru.practicum.android.diploma.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.domain.models.Vacancy as DomainVacancy
import java.io.IOException

class VacancyRepository(
    private val networkClient: HeadHunterNetworkClient,
    private val vacancyMapper: VacancyResponseToDomainMapper
) {

    suspend fun getVacancies(filters: Map<String, String>): NetworkResponse<List<DomainVacancy>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getVacancies(filters)
                if (response.isSuccessful) {
                    val vacancies: List<Vacancy> = response.body()?.items ?: emptyList()
                    val domainVacancies = vacancyMapper.map(vacancies)
                    NetworkResponse(domainVacancies, SUCCESS_CODE)
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

    suspend fun getVacancyDetails(id: String): NetworkResponse<DomainVacancy> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getVacancy(id)
                if (response.isSuccessful) {
                    val vacancyDetails: VacancyDetails = response.body() ?: throw NotFoundException("Vacancy not found")
                    val domainVacancyDetails = vacancyMapper.map(vacancyDetails)
                    NetworkResponse(domainVacancyDetails, SUCCESS_CODE)
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
        private const val SUCCESS_CODE = 200
        private const val ERROR_CODE = 400
    }
}

data class NetworkResponse<T>(
    val data: T?,
    val resultCode: Int,
    val errorMessage: String? = null
)

class NotFoundException(message: String) : Exception(message)
