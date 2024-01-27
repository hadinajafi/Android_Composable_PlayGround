package com.example.moneymanager.models

import java.util.UUID

data class TransactionDto(
    val uuid: UUID = UUID.randomUUID(),
    val title: String,
    val description: String?,
    val amount: Float,
) {

}