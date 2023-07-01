package com.example.oechapp.data.datasource.api

interface RemoteAuthDataSource {
    suspend fun register(
        email: String,
        password: String,
        phone: String,
        fullName: String
    ): Boolean

    suspend fun login(email: String, password: String): Boolean

    suspend fun logout()
}