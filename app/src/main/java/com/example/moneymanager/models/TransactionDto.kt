package com.example.moneymanager.models

import java.sql.Timestamp
import java.util.UUID

data class TransactionDto(
    val uuid: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String?,
    val amount: Float,
    val createdAt: Long,
    val updatedAt: Long,
)