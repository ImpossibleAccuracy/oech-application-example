package com.example.oechapp.data.datasource.api

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject

class RemoteAccountDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : RemoteAccountDataSource {
    override suspend fun getAccountName(): String? {
        return try {
            val user = client.gotrue.retrieveUserForCurrentSession(true)

            user.userMetadata?.let { meta ->
                val fullName = meta["fullName"]
                    ?: return@let null

                fullName.jsonPrimitive.content
            }
        } catch (t: Throwable) {
            Log.e(RemoteAuthDataSource::class.simpleName, t.message ?: "Error handled")
            null
        }
    }
}