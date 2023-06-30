package com.example.oechapp.data.datasource.api

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceModule {
    @Binds
    fun provideRidersDataSource(ridersDataSourceImpl: RemoteRidersDataSourceImpl): RemoteRidersDataSource

    @Binds
    fun provideAuthDataSource(authDataSourceImpl: RemoteAuthDataSourceImpl): RemoteAuthDataSource

    @Binds
    fun provideAccountDataSource(accountDataSourceImpl: RemoteAccountDataSourceImpl): RemoteAccountDataSource
}