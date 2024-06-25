package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.domain.favorites.FavoritesVacancyInteractor
import ru.practicum.android.diploma.domain.favorites.impl.FavoritesVacancyInteractorImpl
import ru.practicum.android.diploma.domain.filtration.api.FiltrationParamsSaveInteractor
import ru.practicum.android.diploma.domain.filtration.impl.FiltrationParamsSaveInteractorImpl
import ru.practicum.android.diploma.domain.search.SearchInteractor
import ru.practicum.android.diploma.domain.search.impl.SearchInteractorImpl
import ru.practicum.android.diploma.domain.vacancy.GetIndustriesInteractor
import ru.practicum.android.diploma.domain.vacancy.GetVacancyDetailsInteractor

val interactorModule = module {

    factory<FavoritesVacancyInteractor> {
        FavoritesVacancyInteractorImpl(get())
    }

    factory<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    factory<GetIndustriesInteractor> {
        GetIndustriesInteractor(get())
    }

    factory<GetVacancyDetailsInteractor> {
        GetVacancyDetailsInteractor(get())
    }

    factory {
        VacancyResponseToDomainMapper()
    }

    factory<FiltrationParamsSaveInteractor> {
        FiltrationParamsSaveInteractorImpl(get())
    }
}
