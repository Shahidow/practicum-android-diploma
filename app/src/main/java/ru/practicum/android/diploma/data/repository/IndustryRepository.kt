package ru.practicum.android.diploma.data.repository

import ru.practicum.android.diploma.data.network.HeadHunterApi
import ru.practicum.android.diploma.domain.search.models.Industry
import ru.practicum.android.diploma.domain.search.models.IndustryGroup

class IndustryRepository(private val api: HeadHunterApi) {

    suspend fun getIndustries(): List<IndustryGroup> {
        val response = api.getIndustries()
        if (response.isSuccessful) {
            return response.body()?.map {
                 IndustryGroup(
                    id = it.id,
                    name = it.name,
                    industries = it.industries.map { industry ->
                       Industry(
                            id = industry.id,
                            name = industry.name
                        )
                    }
                )
            } ?: emptyList()
        } else {
            throw Exception("Failed to load industries")
        }
    }
}

