package ru.practicum.android.diploma.presentation.filtration.place.region

import ru.practicum.android.diploma.domain.filtration.models.AreaDomain

sealed class FilterRegionViewState {
    object NoInternetConnection : FilterRegionViewState()
    object ListOfRegionIsEmpty : FilterRegionViewState()
    object NotFoundRegionInRegionInList : FilterRegionViewState()
    data class ListOfRegion(val listOfRegion: List<AreaDomain>) : FilterRegionViewState()
}
