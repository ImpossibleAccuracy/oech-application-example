package com.example.oechapp.data.model

import java.time.Instant

data class Review(
    val rider: Rider,
    val customer: Customer,
    val rating: Float,
    val note: String?,
    val createAt: Instant
)
