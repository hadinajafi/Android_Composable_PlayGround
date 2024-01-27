package com.example.moneymanager.models

import java.util.UUID

data class Transaction(
    val uuid: UUID = UUID.randomUUID(),
    val title: String,
    val description: String?,
    val amount: Float,
) {

}