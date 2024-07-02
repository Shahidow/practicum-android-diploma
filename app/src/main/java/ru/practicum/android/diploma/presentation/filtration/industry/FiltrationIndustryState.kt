package ru.practicum.android.diploma.presentation.filtration.industry

import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

sealed class FiltrationIndustryState {
    data class Success(val industriesList: List<IndustryDomain>) : FiltrationIndustryState()
    data object Loading : FiltrationIndustryState()
    data object ServerError : FiltrationIndustryState()
    data object Selected : FiltrationIndustryState()
    data object Empty : FiltrationIndustryState()
    data object InternetConnectionError : FiltrationIndustryState()
}
