package ru.practicum.android.diploma.data.favorites.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.db.VacancyConverter
import ru.practicum.android.diploma.data.favorites.FavoritesVacancyRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import java.io.IOException

class FavoritesVacancyRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyConverter: VacancyConverter
) : FavoritesVacancyRepository {

    private val teg = "favorites"
    override suspend fun getAllFavoritesVacancy() = flow {
        emit(withContext(Dispatchers.IO) {
            try {
                appDatabase.favoriteVacancyDao().getFavorites().map { vacancy -> vacancyConverter.map(vacancy) }
            } catch (e: IOException) {
                Log.e(teg, "Caught exception:  ${e.message}")
                null
            }
        })
    }

    override suspend fun getOneFavoriteVacancy(vacancyId: String): DomainVacancy {
        TODO()
    }

    override suspend fun deleteFavoriteVacancy(vacancy: DomainVacancy): Int {
        TODO()
    }

    override suspend fun insertFavoriteVacancy(vacancy: DomainVacancy) {
        TODO()
    }

    override suspend fun getFavoriteIds(): List<String> {
        TODO()
    }
}

