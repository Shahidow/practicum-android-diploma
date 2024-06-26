package ru.practicum.android.diploma.domain.filtration

import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.util.Resource

interface FiltrationInteractor {
    suspend fun getIndustries(): Resource<List<IndustryDomain>>
    suspend fun getAreas(isCountry: Boolean): Resource<List<AreaDomain>>
}
