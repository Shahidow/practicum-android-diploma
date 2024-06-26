package ru.practicum.android.diploma.presentation.filtration.place.region

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.SERVER_ERROR

class FilterRegionViewModel(private val interactor: FiltrationInteractor) : ViewModel() {

    private val _filterRegionState = MutableLiveData<FilterRegionViewState>()
    val filterRegionState: LiveData<FilterRegionViewState> get() = _filterRegionState

    private var regionList = listOf<AreaDomain>()
    fun searchRegionInList(text: String) {
        if (regionList.isNotEmpty()) {
            if (text != "") {
                val filtered = regionList.filter { it.name.contains(text, ignoreCase = true) }
                if (filtered.isNotEmpty()) {
                    _filterRegionState.postValue(FilterRegionViewState.ListOfRegion(listOfRegion = filtered))
                } else {
                    _filterRegionState.postValue(FilterRegionViewState.NotFoundRegionInRegionInList)
                }
            } else {
                _filterRegionState.postValue(FilterRegionViewState.ListOfRegion(listOfRegion = regionList))
            }
        } else {
            _filterRegionState.postValue(FilterRegionViewState.ListOfRegionIsEmpty)
        }
    }

    fun getRegions(parentId: String?) {
        viewModelScope.launch {
            val result = interactor.getAreas(false)
            if (result.data != null) {
                regionList = result.data
                Log.i("123", parentId.toString())
                if (parentId != null) {
                    regionList = filterRegions(parentId, regionList)
                    _filterRegionState.postValue(FilterRegionViewState.ListOfRegion(regionList))
                } else {
                    _filterRegionState.postValue(FilterRegionViewState.ListOfRegion(regionList))
                }
            } else if (result.resultCode != null) {
                when (result.resultCode) {
                    SERVER_ERROR -> {
                        // ошибка сервера
                    }

                    INTERNET_ERROR -> {
                        // нет интернета
                    }
                }
            }
        }
    }

    private fun filterRegions(parentId: String, regions: List<AreaDomain>): List<AreaDomain> {
        val filtered = regions.filter { it.parentId == parentId }
        return filtered
    }
}
