package ru.practicum.android.diploma.presentation.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filtration.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.filtration.models.FilterParams

class FiltrationViewModel(private val paramsInteractor: FiltrationParamsSaveInteractor) : ViewModel() {

    private var _resetButtonState = MutableLiveData<Boolean>()
    val resetButtonState: LiveData<Boolean> = _resetButtonState
    private var _comparisonState = MutableLiveData<Boolean>()
    val comparisonState: LiveData<Boolean> = _comparisonState
    private var _filtersState = MutableLiveData<FilterParams?>()
    val filtersState: LiveData<FilterParams?> = _filtersState
    private var filterParams: FilterParams? = null

    fun getData() {
        val filters = paramsInteractor.getFilterParams()
        filterParams = filters
        _filtersState.postValue(filters)
    }

    fun setData(filters: FilterParams) {
        paramsInteractor.saveFilterParams(filters)
    }

    fun compareFilters(filters: FilterParams) {
        _comparisonState.postValue(filters == filterParams)
    }

    fun resetButtonVisibility(filters: FilterParams) {
        val emptyFilters = FilterParams(null, null, null, null, false)
        _resetButtonState.postValue(filters == emptyFilters)
    }
}
