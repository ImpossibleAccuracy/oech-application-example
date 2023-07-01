package com.example.oechapp.data.datasource.api

interface RemoteAccountDataSource {
    suspend fun getAccountName(): String?
    suspend fun getAccountBalance(): Int
    suspend fun isAuthenticated(): Boolean
}