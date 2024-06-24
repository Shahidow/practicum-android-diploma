package ru.practicum.android.diploma.presentation.filtration.place.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FilterRegionViewModel : ViewModel() {

    private val _filterRegionState = MutableLiveData<FilterRegionViewState>()
    val filterRegionState: LiveData<FilterRegionViewState> get() = _filterRegionState

    private var regionList = listOf<String>()
    private fun searchRegionInList(text: String) {
        if (regionList.isNotEmpty()) {
            if (text != "") {
                val filtered = regionList.filter { it.contains(text) }
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
}
