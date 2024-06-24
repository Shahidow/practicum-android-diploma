package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.data.dto.VacancyDetails
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

class VacancyResponseToDomainMapper {

    fun map(vacancies: List<Vacancy>): List<DomainVacancy> {
        return vacancies.map {
            DomainVacancy(
                vacancyId = it.id,
                name = it.name,
                city = it.area.name,
                area = it.area.name,
                salaryFrom = salaryAmount(it.salary?.from),
                salaryTo = salaryAmount(it.salary?.to),
                salaryCurrency = currencyName(it.salary?.currency),
                employerName = it.employer.name,
                employerLogoUrl = it.employer.logoUrls?.logo,
                experience = null,
                employment = null,
                schedule = null,
                description = "",
                skills = emptyList(),
                contactEmail = null,
                contactName = null,
                contactPhoneNumbers = emptyList(),
                contactComment = emptyList(),
                url = it.url,
                isFavorite = null
            )
        }
    }

    fun map(vacancyDetails: VacancyDetails, isFavorite: Boolean): DomainVacancy {
        return DomainVacancy(
            vacancyId = vacancyDetails.id,
            name = vacancyDetails.name,
            city = vacancyDetails.area.name,
            area = vacancyDetails.area.name,
            salaryFrom = salaryAmount(vacancyDetails.salary?.from),
            salaryTo = salaryAmount(vacancyDetails.salary?.to),
            salaryCurrency = currencyName(vacancyDetails.salary?.currency),
            employerName = vacancyDetails.employer.name,
            employerLogoUrl = vacancyDetails.employer.logoUrls?.logo,
            experience = vacancyDetails.experience?.name,
            employment = vacancyDetails.employment?.name,
            schedule = vacancyDetails.schedule?.name,
            description = vacancyDetails.description ?: "",
            skills = vacancyDetails.keySkills?.map { it.name } ?: emptyList(),
            contactEmail = vacancyDetails.contacts?.email,
            contactName = vacancyDetails.contacts?.name,
            contactPhoneNumbers = emptyList(),
            contactComment = emptyList(),
            url = vacancyDetails.url,
            isFavorite = isFavorite
        )
    }

    private fun currencyName (currencyName: String?): String? {
        return when(currencyName) {
            "AZN" -> "₼"
            "EUR" -> "€"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "m"
            else -> currencyName
        }
    }

    private fun salaryAmount (number: Int?): String? {
        return if (number != null) {
            val formattedNumber = "%,d".format(number).replace(',', ' ')
            formattedNumber
        } else {
            null
        }
    }
}
