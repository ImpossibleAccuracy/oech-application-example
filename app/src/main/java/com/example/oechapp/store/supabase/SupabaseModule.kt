package com.example.oechapp.store.supabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.InetSocketAddress
import java.net.Proxy

@Module
@InstallIn(SingletonComponent::class)
object SupabaseModule {
    @Provides
    fun provideHttpClient() = runBlocking {
        try {
            withContext(Dispatchers.IO) {
                HttpClient(Android) {
                    engine {
                        connectTimeout = 100_000
                        socketTimeout = 100_000
                        proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress("localhost", 8080))
                    }
                }
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
    }

    @Provides
    fun provideSupabaseClient(httpClient: HttpClient) =
        try {
            createSupabaseClient(
                supabaseUrl = "https://eojrsogefmmctzglurjv.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImVvanJzb2dlZm1tY3R6Z2x1cmp2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODgzNzEzMzksImV4cCI6MjAwMzk0NzMzOX0.zsV8O4qqcrERGrnwHQ69npNG1t3EWMdtdKumsqPRyHo"
            ) {

                install(Postgrest)
                install(GoTrue)

                // TODO
//                httpConfig {
//                    install(httpClient)
//                }

//                install(Realtime) {
//                    reconnectDelay = 5.seconds
//                }

            }
        } catch (t: Throwable) {
            t.printStackTrace()
            throw t
        }
}