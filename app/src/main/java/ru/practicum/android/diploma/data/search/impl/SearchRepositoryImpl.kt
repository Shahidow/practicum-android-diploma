package ru.practicum.android.diploma.data.search.impl

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.dto.VacancyRequest
import ru.practicum.android.diploma.data.mappers.VacancyResponseToDomainMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.data.search.SearchRepository
import ru.practicum.android.diploma.domain.search.models.DomainVacancy
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SERVER_ERROR

class SearchRepositoryImpl(
    private val networkClient: HeadHunterNetworkClient,
    private val context: Context,
    private val converter: VacancyResponseToDomainMapper
) : SearchRepository {
    override var currentPage: Int? = null
    override var foundItems: Int? = null
    override var pages: Int? = null
    override fun searchVacancies(text: String, page: Int): Flow<Resource<List<DomainVacancy>>> = flow {
        if (!isConnected()) {
            emit(Resource.Error(INTERNET_ERROR))
        } else {
            val response = networkClient.getVacancies(VacancyRequest(text, page).map())
            if (response.isSuccessful) {
                with(response.body()) {
                    currentPage = this?.page
                    foundItems = this?.found
                    val data = this?.items?.let { converter.map(it) }
                    this@SearchRepositoryImpl.pages = this?.pages
                    emit(Resource.Success(data))
                }
            } else {
                emit(Resource.Error(SERVER_ERROR))
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        var isConnected = false
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> isConnected = true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> isConnected = true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> isConnected = true
            }
        }
        return isConnected
    }

}
