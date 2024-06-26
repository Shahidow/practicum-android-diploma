package ru.practicum.android.diploma.data.mappers

import ru.practicum.android.diploma.data.dto.models.Areas
import ru.practicum.android.diploma.data.dto.models.Industry
import ru.practicum.android.diploma.data.dto.models.IndustryItem
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class FiltersMapper {

    fun industryMap(industries: List<Industry>): List<IndustryDomain> {
        return industries.map {
            IndustryDomain(
                id = it.id,
                name = it.name,
                industryList = industryItemMap(it.industries)
            )
        }
    }

    private fun industryItemMap(industries: List<IndustryItem>): List<IndustryDomain> {
        return industries.map {
            IndustryDomain(
                id = it.id,
                name = it.name,
                industryList = emptyList()
            )
        }
    }

    fun areaMap(areas: List<Areas>): List<AreaDomain> {
        return areas.map {
            AreaDomain(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = regionMap(it.areas)
            )
        }
    }

    private fun regionMap(areas: List<Areas>): List<AreaDomain> {
        return areas.map {
            AreaDomain(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                areas = emptyList()
            )
        }
    }
}
