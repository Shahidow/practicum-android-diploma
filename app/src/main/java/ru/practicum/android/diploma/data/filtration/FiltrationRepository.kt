package ru.practicum.android.diploma.data.filtration

import ru.practicum.android.diploma.data.dto.NetworkResponse
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

interface FiltrationRepository {
    suspend fun getIndustries(): NetworkResponse<List<IndustryDomain>>
    suspend fun getAreas(): NetworkResponse<List<AreaDomain>>
}
