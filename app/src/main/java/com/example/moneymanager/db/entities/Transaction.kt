package com.example.moneymanager.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(index = true)
    val uuid: UUID = UUID.randomUUID(),
    val title: String,
    val description: String? = null,
    val amount: Float,
)