package com.example.oechapp.data.datasource.api

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteAccountDataSourceImpl @Inject constructor(
    private val client: SupabaseClient
) : RemoteAccountDataSource {
    private var user: UserInfo? = null

    private suspend fun refreshUser() {
        user = client.gotrue.retrieveUserForCurrentSession(true)
    }

    private suspend fun getUser(): UserInfo? {
        if (user == null) refreshUser()

        return user
    }

    override suspend fun isAuthenticated(): Boolean =
        try {
            getUser() != null
        } catch (t: Throwable) {
            Log.e(RemoteAuthDataSource::class.simpleName, t.message ?: "Error handled")
            false
        }

    override suspend fun getAccountName(): String? {
        return try {
            getUser()?.userMetadata?.let { meta ->
                val fullName = meta["fullName"]
                    ?: return@let null

                fullName.jsonPrimitive.content
            }
        } catch (t: Throwable) {
            Log.e(RemoteAuthDataSource::class.simpleName, t.message ?: "Error handled")
            null
        }
    }

    override suspend fun getAccountBalance(): Int {
        return 123123
    }
}