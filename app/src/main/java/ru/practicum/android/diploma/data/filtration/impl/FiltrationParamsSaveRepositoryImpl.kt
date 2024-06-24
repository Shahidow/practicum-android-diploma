package ru.practicum.android.diploma.data.filtration.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.filtration.FiltrationParamsSaveRepository
import ru.practicum.android.diploma.util.FILTRATION_KEY

class FiltrationParamsSaveRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : FiltrationParamsSaveRepository {
    override fun getFilterParams(): List<String> {
        val json = sharedPreferences.getString(FILTRATION_KEY, null) ?: return emptyList()
        return Gson().fromJson(json, Array<String>::class.java).toList()
    }

    override fun saveFilterParams(listFilterParams: List<String>) {
        removeFilterParamsInSharedPreferences()
        val json = Gson().toJson(listFilterParams)
        sharedPreferences.edit()
            .putString(FILTRATION_KEY, json)
            .apply()
    }

    override fun insertFilterParam(filterParam: String) {
        val listOfParams = getFilterParams().toMutableList()
        listOfParams.add(filterParam)
        saveFilterParams(listOfParams)
    }

    private fun removeFilterParamsInSharedPreferences() {
        sharedPreferences.edit()
            .remove(FILTRATION_KEY)
            .apply()
    }
}
