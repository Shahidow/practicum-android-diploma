package ru.practicum.android.diploma.presentation.filtration.place.country

sealed class FilterCountryViewState {
    object EmptyCountryList : FilterCountryViewState()
    data class CountryList(val countryList: List<String>) : FilterCountryViewState()
}
