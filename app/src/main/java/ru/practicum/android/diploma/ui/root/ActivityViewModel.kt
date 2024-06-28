package ru.practicum.android.diploma.ui.root

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain

open class ActivityViewModel : ViewModel() {
    val region: MutableLiveData<AreaDomain> by lazy {
        MutableLiveData<AreaDomain>()
    }
    val country: MutableLiveData<AreaDomain> by lazy {
        MutableLiveData<AreaDomain>()
    }
    val regionFilter: MutableLiveData<AreaDomain> by lazy {
        MutableLiveData<AreaDomain>()
    }
    val countryFilter: MutableLiveData<AreaDomain> by lazy {
        MutableLiveData<AreaDomain>()
    }
    val industry: MutableLiveData<IndustryDomain> by lazy {
        MutableLiveData<IndustryDomain>()
    }
}
