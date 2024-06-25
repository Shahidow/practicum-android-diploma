package ru.practicum.android.diploma.data.filtration.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.filtration.FiltrationParamsSaveRepository
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.FilterRegionArea
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.util.FILTRATION_AREA_KEY
import ru.practicum.android.diploma.util.FILTRATION_INDUSTRY_KEY
import ru.practicum.android.diploma.util.FILTRATION_REGION_KEY

class FiltrationParamsSaveRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : FiltrationParamsSaveRepository {
    override fun getAreaFilterParams(): AreaDomain? {
        val json = sharedPreferences.getString(FILTRATION_AREA_KEY, null) ?: return null
        return Gson().fromJson(json, AreaDomain::class.java)
    }

    override fun saveAreaFilterParams(areaDomain: AreaDomain) {
        removeFilterParamsInSharedPreferences(FILTRATION_AREA_KEY)
        val json = Gson().toJson(areaDomain)
        sharedPreferences.edit()
            .putString(FILTRATION_AREA_KEY, json)
            .apply()
    }

    override fun getRegionFilterParams(): FilterRegionArea? {
        val json = sharedPreferences.getString(FILTRATION_REGION_KEY, null) ?: return null
        return Gson().fromJson(json, FilterRegionArea::class.java)
    }

    override fun saveRegionFilterParams(filterRegionArea: FilterRegionArea) {
        removeFilterParamsInSharedPreferences(FILTRATION_REGION_KEY)
        val json = Gson().toJson(filterRegionArea)
        sharedPreferences.edit()
            .putString(FILTRATION_AREA_KEY, json)
            .apply()
    }

    override fun getIndustryFilterParams(): IndustryDomain? {
        val json = sharedPreferences.getString(FILTRATION_INDUSTRY_KEY, null) ?: return null
        return Gson().fromJson(json, IndustryDomain::class.java)
    }

    override fun saveIndustryFilterParams(industryDomain: IndustryDomain) {
        removeFilterParamsInSharedPreferences(FILTRATION_INDUSTRY_KEY)
        val json = Gson().toJson(industryDomain)
        sharedPreferences.edit()
            .putString(FILTRATION_AREA_KEY, json)
            .apply()
    }

    private fun removeFilterParamsInSharedPreferences(key: String) {
        sharedPreferences.edit()
            .remove(key)
            .apply()
    }
}
