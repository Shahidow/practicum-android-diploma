package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesVacancyRepository {
    suspend fun getAllFavoritesVacancy(): Flow<List<Vacancy>?>
    suspend fun getOneFavoriteVacancy(vacancyId: String): Vacancy
    suspend fun deleteFavoriteVacancy(vacancy: Vacancy): Int
    suspend fun insertFavoriteVacancy(vacancy: Vacancy)
    suspend fun getFavoriteIds(): List<String>
}