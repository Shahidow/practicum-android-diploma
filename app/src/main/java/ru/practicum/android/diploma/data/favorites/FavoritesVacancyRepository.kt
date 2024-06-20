package ru.practicum.android.diploma.data.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.search.models.DomainVacancy

interface FavoritesVacancyRepository {
    suspend fun getAllFavoritesVacancy(): Flow<List<DomainVacancy>?>
    suspend fun getOneFavoriteVacancy(vacancyId: String): DomainVacancy
    suspend fun deleteFavoriteVacancy(vacancy: DomainVacancy): Int
    suspend fun insertFavoriteVacancy(vacancy: DomainVacancy)
    suspend fun getFavoriteIds(): List<String>
}
