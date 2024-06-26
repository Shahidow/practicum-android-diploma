package ru.practicum.android.diploma.data.filtration

import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

interface FiltrationParamsSaveRepository {
    fun getAreaFilterParams(): AreaDomain?
    fun saveAreaFilterParams(areaDomain: AreaDomain)
    fun getRegionFilterParams(): AreaDomain?
    fun saveRegionFilterParams(filterRegionArea: AreaDomain)
    fun getIndustryFilterParams(): IndustryDomain?
    fun saveIndustryFilterParams(industryDomain: IndustryDomain)
}
