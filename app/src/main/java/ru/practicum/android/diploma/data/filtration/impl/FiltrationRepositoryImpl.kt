package ru.practicum.android.diploma.data.filtration.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.dto.NetworkResponse
import ru.practicum.android.diploma.data.filtration.FiltrationRepository
import ru.practicum.android.diploma.data.mappers.FiltersMapper
import ru.practicum.android.diploma.data.network.HeadHunterNetworkClient
import ru.practicum.android.diploma.domain.filtration.models.AreaDomain
import ru.practicum.android.diploma.domain.filtration.models.IndustryDomain
import ru.practicum.android.diploma.util.INTERNET_ERROR
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.SUCCESS_CODE
import java.io.IOException

class FiltrationRepositoryImpl(
    private val networkClient: HeadHunterNetworkClient,
    private val filtersMapper: FiltersMapper,
) : FiltrationRepository {

    override suspend fun getIndustries(): NetworkResponse<List<IndustryDomain>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getIndustries()
                if (response.isSuccessful) {
                    val industryDomain: List<IndustryDomain>? =
                        response.body()?.let { filtersMapper.industryMap(it) }
                    NetworkResponse(industryDomain, SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: R.string.server_error.toString()
                    NetworkResponse(null, SERVER_ERROR, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(null, INTERNET_ERROR, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(null, SERVER_ERROR, "HTTP error: ${ex.message}")
            }
        }
    }

    override suspend fun getAreas(): NetworkResponse<List<AreaDomain>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = networkClient.getAreas()
                if (response.isSuccessful) {
                    val areasDomain: List<AreaDomain>? =
                        response.body()?.let { filtersMapper.areaMap(it) }
                    NetworkResponse(areasDomain, SUCCESS_CODE)
                } else {
                    val errorBody = response.errorBody()?.string() ?: R.string.server_error.toString()
                    NetworkResponse(null, SERVER_ERROR, errorBody)
                }
            } catch (ex: IOException) {
                NetworkResponse(null, INTERNET_ERROR, "Network error: ${ex.message}")
            } catch (ex: HttpException) {
                NetworkResponse(null, SERVER_ERROR, "HTTP error: ${ex.message}")
            }
        }
    }
}
