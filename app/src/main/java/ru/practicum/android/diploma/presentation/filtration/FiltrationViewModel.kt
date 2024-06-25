package ru.practicum.android.diploma.presentation.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

class FiltrationViewModel : ViewModel() {
    private val _industry = MutableLiveData<IndustryDomain?>()
    val industry: LiveData<IndustryDomain?> get() = _industry

    private val _workplace = MutableLiveData<String?>()
    val workplace: LiveData<String?> get() = _workplace

//    fun setIndustry(industry: IndustryGroup) {
//        _industry.value = industry
//    }
//
//    fun setWorkplace(workplace: String) {
//        _workplace.value = workplace
//    }

    fun resetFilters() {
        _industry.value = null
        _workplace.value = null
    }
}
