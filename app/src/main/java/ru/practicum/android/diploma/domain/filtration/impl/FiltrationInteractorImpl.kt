package ru.practicum.android.diploma.domain.filtration.impl

import ru.practicum.android.diploma.data.filtration.FiltrationRepository
import ru.practicum.android.diploma.domain.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.SUCCESS_CODE

class FiltrationInteractorImpl(private val repository: FiltrationRepository) : FiltrationInteractor {

    override suspend fun getIndustries(): Resource<List<IndustryDomain>> {
        val result = repository.getIndustries()
        return when (result.resultCode) {
            SUCCESS_CODE -> Resource.Success(result.data?.let { getDomainIndustries(it) })
            INTERNET_ERROR -> Resource.Error(result.resultCode)
            SERVER_ERROR -> Resource.Error(result.resultCode)
            else -> Resource.Error(result.resultCode)
        }
    }

    override suspend fun getAreas(): Resource<List<AreaDomain>> {
        val result = repository.getAreas()
        return when (result.resultCode) {
            SUCCESS_CODE -> Resource.Success(result.data)
            INTERNET_ERROR -> Resource.Error(result.resultCode)
            SERVER_ERROR -> Resource.Error(result.resultCode)
            else -> Resource.Error(result.resultCode)
        }
    }

    private fun getDomainIndustries(industries: List<IndustryDomain>): List<IndustryDomain> {
        val industriesList = mutableListOf<IndustryDomain>()
        industries.forEach { item ->
            industriesList.addAll(item.industryList)
        }
        return industriesList.sortedBy { it.name }
    }

}
