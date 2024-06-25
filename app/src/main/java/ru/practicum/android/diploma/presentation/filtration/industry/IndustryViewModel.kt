package ru.practicum.android.diploma.presentation.filtration.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class IndustryViewModel(
    private val industryInteractor: FiltrationInteractor,
) : ViewModel() {

    // Переменная для хранения списка отраслей
    private val _industries = MutableLiveData<List<IndustryDomain>>()
    val industries: LiveData<List<IndustryDomain>> get() = _industries

    // Переменная для хранения выбранной отрасли
    private val _selectedIndustry = MutableLiveData<IndustryDomain?>()
    val selectedIndustry: LiveData<IndustryDomain?> get() = _selectedIndustry

    init {
        loadIndustries()
    }

    // Метод для загрузки отраслей
    private fun loadIndustries() {
        viewModelScope.launch {
            val industries = industryInteractor.getIndustries()
            if (industries.data != null) {
                _industries.value = industries.data!!
            }

        }
    }

    // Метод для установки выбранной отрасли
    fun setSelectedIndustry(industry: IndustryDomain?) {
        _selectedIndustry.value = industry
    }

    // Метод для фильтрации списка отраслей
    fun filterIndustries(query: String) {
        val filteredList = _industries.value?.filter {
            it.name.contains(query, ignoreCase = true)
        }
        _industries.value = filteredList ?: emptyList()
    }
}
