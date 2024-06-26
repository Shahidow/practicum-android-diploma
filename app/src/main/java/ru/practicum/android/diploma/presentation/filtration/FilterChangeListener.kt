package ru.practicum.android.diploma.presentation.filtration

interface FilterChangeListener {
    fun onFilterChanged(filterParams: Map<String, String>)
}
