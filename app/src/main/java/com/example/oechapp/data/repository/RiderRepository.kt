package com.example.oechapp.data.repository

import com.example.oechapp.data.datasource.api.RemoteRidersDataSource
import com.example.oechapp.data.model.Resource
import com.example.oechapp.data.model.Rider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RiderRepository @Inject constructor(
    private val remoteRidersDataSource: RemoteRidersDataSource
) {
    fun getRiders(page: Int): Flow<Resource<List<Rider>>> = flow {
        emit(Resource.Loading)

        try {
            val result = remoteRidersDataSource.getPage(page)
            emit(Resource.Success(result))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}