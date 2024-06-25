package ru.practicum.android.diploma.presentation.filtration.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.search.models.IndustryGroup
import ru.practicum.android.diploma.domain.vacancy.GetIndustriesInteractor

class IndustryViewModel(
    private val industryInteractor: GetIndustriesInteractor
) : ViewModel() {

    // Переменная для хранения списка отраслей
    private val _industries = MutableLiveData<List<IndustryGroup>>()
    val industries: LiveData<List<IndustryGroup>> get() = _industries

    // Переменная для хранения выбранной отрасли
    private val _selectedIndustry = MutableLiveData<IndustryGroup?>()
    val selectedIndustry: LiveData<IndustryGroup?> get() = _selectedIndustry

    init {
        loadIndustries()
    }

    // Метод для загрузки отраслей
    private fun loadIndustries() {
        viewModelScope.launch {
            val industries = industryInteractor.getIndustries()
            _industries.value = industries
        }
    }

    // Метод для установки выбранной отрасли
    fun setSelectedIndustry(industry: IndustryGroup?) {
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
