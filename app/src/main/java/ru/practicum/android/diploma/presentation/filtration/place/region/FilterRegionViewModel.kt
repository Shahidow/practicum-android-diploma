package ru.practicum.android.diploma.presentation.filtration.place.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filtration.models.FilterRegionArea

class FilterRegionViewModel : ViewModel() {

    private val _filterRegionState = MutableLiveData<FilterRegionViewState>()
    val filterRegionState: LiveData<FilterRegionViewState> get() = _filterRegionState

    private var regionList = listOf<FilterRegionArea>()
    fun searchRegionInList(text: String) {
        if (regionList.isNotEmpty()) {
            if (text != "") {
                val filtered = regionList.filter { region -> region.name.contains(text) }
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

    fun getRegions() {
        /*  viewModelScope.launch {
               filterRegionInteractor.getRegions().collect{
                   // ответ от сервера. добавляем в список все регионы
                   // _filterRegionState.postValue(FilterRegionViewState.ListOfRegion(listOfRegion = regionList))
               }
           }
       */
    }


    fun insertFilterRegion(area: FilterRegionArea) {
        // filtrationParamsSaveInteractor.saveFilterParams()
    }
}
