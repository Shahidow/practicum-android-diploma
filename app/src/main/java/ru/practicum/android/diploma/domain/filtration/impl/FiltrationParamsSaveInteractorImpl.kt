package ru.practicum.android.diploma.domain.filtration.impl

import ru.practicum.android.diploma.data.filtration.FiltrationParamsSaveRepository
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor

class FiltrationParamsSaveInteractorImpl(
    private val filtrationParamsSaveRepository: FiltrationParamsSaveRepository
) : FiltrationParamsSaveInteractor {
    override fun getFilterParams(): List<String> {
        return filtrationParamsSaveRepository.getFilterParams()
    }

    override fun saveFilterParams(listFilterParams: List<String>) {
        filtrationParamsSaveRepository.saveFilterParams(listFilterParams)
    }
}
