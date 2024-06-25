package ru.practicum.android.diploma.data.filtration

import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.FilterRegionArea
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

interface FiltrationParamsSaveRepository {
    fun getAreaFilterParams(): AreaDomain?
    fun saveAreaFilterParams(areaDomain: AreaDomain)
    fun getRegionFilterParams(): FilterRegionArea?
    fun saveRegionFilterParams(filterRegionArea: FilterRegionArea)
    fun getIndustryFilterParams(): IndustryDomain?
    fun saveIndustryFilterParams(industryDomain: IndustryDomain)
}
