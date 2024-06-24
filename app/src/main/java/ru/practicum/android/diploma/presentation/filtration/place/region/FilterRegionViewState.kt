package ru.practicum.android.diploma.presentation.filtration.place.region

import ru.practicum.android.diploma.domain.filtration.models.FilterRegionArea

sealed class FilterRegionViewState {
    object ListOfRegionIsEmpty : FilterRegionViewState()
    object NotFoundRegionInRegionInList : FilterRegionViewState()
    data class ListOfRegion(val listOfRegion: List<FilterRegionArea>) : FilterRegionViewState()
}
