package ru.practicum.android.diploma.domain.vacancy

import ru.practicum.android.diploma.data.vacancy.VacancyRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.SUCCESS_CODE

class GetVacancyDetailsInteractor(private val repository: VacancyRepository) {

    suspend fun execute(vacancyId: String): Resource<DomainVacancy> {
        val result = repository.getVacancyDetails(vacancyId)
        return when (result.resultCode) {
            SUCCESS_CODE -> {
                Resource.Success(result.data)
            }

            INTERNET_ERROR -> {
                Resource.Error(result.resultCode)
            }

            SERVER_ERROR -> {
                Resource.Error(result.resultCode)
            }

            else -> {
                Resource.Error(result.resultCode)
            }
        }
    }
}
