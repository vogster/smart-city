package ru.bullyboo.domain.entity

import java.io.Serializable

data class BidsData(
    val id: Int?,
    val title: String?,
    val description: String?,
    val created_at: String?,
    val rating: Int?,
    val status: String?,
): Serializable