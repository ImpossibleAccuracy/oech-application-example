package com.example.oechapp.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object ApplicationDataStore {
    val IS_ONBOARD_VISITED = booleanPreferencesKey("IsOnboardVisited")
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")

