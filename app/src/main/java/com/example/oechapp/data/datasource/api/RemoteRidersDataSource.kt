package com.example.oechapp.data.datasource.api

import com.example.oechapp.data.model.Rider

interface RemoteRidersDataSource {
    fun getPage(page: Int): List<Rider>
}