package ru.practicum.android.diploma.data.vacancy

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.NetworkResponse
import ru.practicum.android.diploma.data.dto.NotFoundException
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.SUCCESS_CODE
import java.io.IOException

class VacancyRepository(
    private val networkClient: HeadHunterNetworkClient,
    private val vacancyMapper: VacancyResponseToDomainMapper,
) {
    suspend fun getVacancyDetails(id: String): NetworkResponse<DomainVacancy> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getVacancy(id)
                if (response.isSuccessful) {
                    val vacancyDetails: VacancyDetails =
                        response.body() ?: throw NotFoundException(R.string.server_error.toString())
                    val domainVacancyDetails = vacancyMapper.map(vacancyDetails, true)
                    NetworkResponse(domainVacancyDetails, SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: R.string.server_error.toString()
                    NetworkResponse(null, SERVER_ERROR, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(null, INTERNET_ERROR, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(null, SERVER_ERROR, "HTTP error: ${ex.message}")
            }
        }
    }
}
