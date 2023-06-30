package com.example.oechapp.data.datasource.api

import com.example.oechapp.data.model.Rider
import javax.inject.Inject

class RemoteRidersDataSourceImpl @Inject constructor() : RemoteRidersDataSource {
    override fun getPage(page: Int): List<Rider> {
        TODO("Not yet implemented")
    }
}