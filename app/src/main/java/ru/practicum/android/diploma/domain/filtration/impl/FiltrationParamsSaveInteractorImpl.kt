package ru.practicum.android.diploma.domain.filtration.impl

import ru.practicum.android.diploma.data.filtration.FiltrationParamsSaveRepository
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.filtration.models.FilterParams

class FiltrationParamsSaveInteractorImpl(
    private val filtrationParamsSaveRepository: FiltrationParamsSaveRepository
) : FiltrationParamsSaveInteractor {
    override fun getFilterParams(): FilterParams? {
        return filtrationParamsSaveRepository.getFilterParams()
    }

    override fun saveFilterParams(filterParams: FilterParams) {
        filtrationParamsSaveRepository.saveFilterParams(filterParams)
    }

    override fun removeFilterParams() {
        filtrationParamsSaveRepository.removeFilterParams()
    }

    override fun hasActiveFilters(): Boolean {
        return filtrationParamsSaveRepository.hasActiveFilters()
    }
}
