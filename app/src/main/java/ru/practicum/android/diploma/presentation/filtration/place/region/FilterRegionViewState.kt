package ru.practicum.android.diploma.presentation.filtration.place.region

sealed class FilterRegionViewState {
    object ListOfRegionIsEmpty : FilterRegionViewState()
    object NotFoundRegionInRegionInList : FilterRegionViewState()
    data class ListOfRegion(val listOfRegion: List<String>) : FilterRegionViewState()
}
