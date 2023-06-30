package com.example.oechapp.data.model

sealed interface Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>

    data class Error<T>(val error: Throwable) : Resource<T>

    object Loading : Resource<Nothing>
}