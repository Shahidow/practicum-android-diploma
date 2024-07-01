package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.presentation.favorites.FavoritesViewModel
import ru.practicum.android.diploma.presentation.filtration.FiltrationViewModel
import ru.practicum.android.diploma.presentation.filtration.industry.IndustryViewModel
import ru.practicum.android.diploma.presentation.filtration.place.FilterPlaceViewModel
import ru.practicum.android.diploma.presentation.filtration.place.country.FilterCountryViewModel
import ru.practicum.android.diploma.presentation.filtration.place.region.FilterRegionViewModel
import ru.practicum.android.diploma.presentation.search.SearchViewModel
import ru.practicum.android.diploma.presentation.vacancy.VacancyViewModel

val appModule = module {

    viewModel<SearchViewModel> {
        SearchViewModel(get(), get(), get(), get())
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModel(get())
    }

    viewModel<VacancyViewModel> {
        VacancyViewModel(get(), get())
    }

    viewModel<IndustryViewModel> {
        IndustryViewModel(get(), get(), get())
    }

    viewModel<FilterRegionViewModel> {
        FilterRegionViewModel(get())
    }

    viewModel<FilterCountryViewModel> {
        FilterCountryViewModel(get())
    }

    viewModel<IndustryViewModel> {
        IndustryViewModel(get(), get(), get())
    }

    viewModel<FilterPlaceViewModel> {
        FilterPlaceViewModel()
    }

    viewModel<FiltrationViewModel> {
        FiltrationViewModel(get())
    }
}
