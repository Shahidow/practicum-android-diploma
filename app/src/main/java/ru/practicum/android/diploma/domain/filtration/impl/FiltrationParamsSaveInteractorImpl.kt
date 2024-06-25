package ru.practicum.android.diploma.domain.filtration.impl

import ru.practicum.android.diploma.data.filtration.FiltrationParamsSaveRepository
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.FilterRegionArea
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class FiltrationParamsSaveInteractorImpl(
    private val filtrationParamsSaveRepository: FiltrationParamsSaveRepository
) : FiltrationParamsSaveInteractor {
    override fun getAreaFilterParams(): AreaDomain? {
        return filtrationParamsSaveRepository.getAreaFilterParams()
    }

    override fun saveAreaFilterParams(areaDomain: AreaDomain) {
        filtrationParamsSaveRepository.saveAreaFilterParams(areaDomain)
    }

    override fun getRegionFilterParams(): FilterRegionArea? {
        return filtrationParamsSaveRepository.getRegionFilterParams()
    }

    override fun saveRegionFilterParams(filterRegionArea: FilterRegionArea) {
        filtrationParamsSaveRepository.saveRegionFilterParams(filterRegionArea)
    }

    override fun getIndustryFilterParams(): IndustryDomain? {
        return filtrationParamsSaveRepository.getIndustryFilterParams()
    }

    override fun saveIndustryFilterParams(industryDomain: IndustryDomain) {
        filtrationParamsSaveRepository.saveIndustryFilterParams(industryDomain)
    }
}
