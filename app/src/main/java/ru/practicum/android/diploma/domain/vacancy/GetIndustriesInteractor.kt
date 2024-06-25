package ru.practicum.android.diploma.domain.vacancy

import ru.practicum.android.diploma.data.repository.IndustryRepository
import ru.practicum.android.diploma.domain.search.models.IndustryGroup

class GetIndustriesInteractor(private val repository: IndustryRepository) {

    suspend fun getIndustries(): List<IndustryGroup> {
        return repository.getIndustries()
    }
}

