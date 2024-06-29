package ru.practicum.android.diploma.presentation.filtration.place.country

import ru.practicum.android.diploma.domain.filtration.models.AreaDomain

sealed class FilterCountryViewState {
    object NoInternetConnection : FilterCountryViewState()
    object EmptyCountryList : FilterCountryViewState()
    data class CountryList(val countryList: List<AreaDomain>) : FilterCountryViewState()
}
