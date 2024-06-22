package ru.practicum.android.diploma.data.vacancy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.NetworkResponse
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.SUCCESS_CODE
import java.io.IOException
import ru.practicum.android.diploma.domain.search.models.DomainVacancy as DomainVacancy

class VacancyRepository(
    private val networkClient: HeadHunterNetworkClient,
    private val vacancyMapper: VacancyResponseToDomainMapper
) {
    suspend fun getVacancyDetails(id: String): NetworkResponse<DomainVacancy> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getVacancy(id)
                if (response.isSuccessful) {
                    val vacancyDetails: VacancyDetails = response.body() ?: throw NotFoundException("Vacancy not found")
                    val domainVacancyDetails = vacancyMapper.map(vacancyDetails, true)
                    NetworkResponse(domainVacancyDetails, SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    NetworkResponse(null, SERVER_ERROR, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(null, SERVER_ERROR, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(null, SERVER_ERROR, "HTTP error: ${ex.message}")
            }
        }
    }
}

class NotFoundException(message: String) : Exception(message)
