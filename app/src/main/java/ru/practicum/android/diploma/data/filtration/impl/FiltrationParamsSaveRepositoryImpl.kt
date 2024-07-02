package ru.practicum.android.diploma.data.filtration.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.filtration.FiltrationParamsSaveRepository
import ru.practicum.android.diploma.domain.filtration.models.FilterParams
import ru.practicum.android.diploma.util.FILTRATION_PARAMS_KEY

class FiltrationParamsSaveRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : FiltrationParamsSaveRepository {
    override fun getFilterParams(): FilterParams? {
        val json = sharedPreferences.getString(FILTRATION_PARAMS_KEY, null) ?: return null
        return Gson().fromJson(json, FilterParams::class.java)
    }

    override fun saveFilterParams(filterParams: FilterParams) {
        removeFilterParams()
        val json = Gson().toJson(filterParams)
        sharedPreferences.edit()
            .putString(FILTRATION_PARAMS_KEY, json)
            .apply()
    }

    override fun removeFilterParams() {
        sharedPreferences.edit()
            .remove(FILTRATION_PARAMS_KEY)
            .apply()
    }

    override fun hasActiveFilters(): Boolean {
        val filters = getFilterParams()
        return filters?.country != null || filters?.region != null || filters?.industry != null ||
            filters?.salary != null || filters?.showOnlyWithSalary == true
    }
}
