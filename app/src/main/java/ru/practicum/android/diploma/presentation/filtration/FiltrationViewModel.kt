package ru.practicum.android.diploma.presentation.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.filtration.models.FilterParams
import ru.practicum.android.diploma.presentation.search.SearchState

class FiltrationViewModel(private val paramsInteractor: FiltrationParamsSaveInteractor) : ViewModel() {

    private var _filtersState = MutableLiveData<FilterParams?>()
    val filtersState: LiveData<FilterParams?> = _filtersState

    fun getData() {
        val filters = paramsInteractor.getFilterParams()
        _filtersState.postValue(filters)
    }

    fun setData(filters: FilterParams) {
        paramsInteractor.saveFilterParams(filters)
    }

}
