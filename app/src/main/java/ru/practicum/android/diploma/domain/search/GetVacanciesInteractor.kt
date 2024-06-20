package ru.practicum.android.diploma.domain.search

import retrofit2.HttpException
import ru.practicum.android.diploma.data.search.VacancyRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import java.io.IOException

class GetVacanciesInteractor(private val repository: VacancyRepository) {

    suspend fun execute(filters: Map<String, String>): Result<List<DomainVacancy>> {
        return try {
            val response = repository.getVacancies(filters)
            if (response.resultCode == SUCCESS_CODE) {
                response.data?.let { Result.success(it) } ?: Result.failure(Exception("Invalid data format"))
            } else {
                Result.failure(Exception(response.errorMessage))
            }
        } catch (e: IOException) {
            Result.failure(Exception("Network error: ${e.message}"))
        } catch (e: HttpException) {
            Result.failure(Exception("HTTP error: ${e.message}"))
        }
    }

    companion object {
        private const val SUCCESS_CODE = 200
    }
}