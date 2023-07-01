package com.example.oechapp.data.datasource.api

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import javax.inject.Inject

class RemoteAuthDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : RemoteAuthDataSource {
    override suspend fun register(
        email: String,
        password: String,
        phone: String,
        fullName: String
    ): Boolean =
        try {
            client.gotrue.signUpWith(Email) {
                this.email = email
                this.password = password

                this.data = buildJsonObject {
                    put("phone", phone)
                    put("fullName", fullName)
                }
            }

            true
        } catch (t: Throwable) {
            Log.e(RemoteAuthDataSource::class.simpleName, t.message ?: "Error handled")
            false
        }

    override suspend fun login(email: String, password: String): Boolean =
        try {
            client.gotrue.loginWith(Email) {
                this.email = email
                this.password = password
            }

            true
        } catch (t: Throwable) {
            Log.e(RemoteAuthDataSource::class.simpleName, t.message ?: "Error handled")
            false
        }

    override suspend fun logout() {
        try {
            client.gotrue.logout()
        } catch (t: Throwable) {
            Log.e(RemoteAuthDataSource::class.simpleName, t.message ?: "Error handled")
            t.printStackTrace()
        }
    }
}