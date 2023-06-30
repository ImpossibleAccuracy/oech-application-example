package com.example.oechapp.data.repository

import com.example.oechapp.data.datasource.api.RemoteAccountDataSource
import com.example.oechapp.data.datasource.api.RemoteAuthDataSource
import com.example.oechapp.data.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val authDataSource: RemoteAuthDataSource,
    private val accountDataSource: RemoteAccountDataSource
) {
    suspend fun getAccountName(): String? =
        withContext(Dispatchers.IO) {
            accountDataSource.getAccountName()
        }
    
    suspend fun isAuthenticated(): Boolean =
        withContext(Dispatchers.IO) {
            authDataSource.isAuthenticated()
        }

    suspend fun register(
        email: String,
        password: String,
        phone: String,
        fullName: String
    ): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val result = authDataSource.register(email, password, phone, fullName)
                Resource.Success(result)
            } catch (t: Throwable) {
                Resource.Error(t)
            }
        }

    suspend fun login(email: String, password: String): Resource<Boolean> =
        withContext(Dispatchers.IO) {
            try {
                val result = authDataSource.login(email, password)
                Resource.Success(result)
            } catch (t: Throwable) {
                Resource.Error(t)
            }
        }
}