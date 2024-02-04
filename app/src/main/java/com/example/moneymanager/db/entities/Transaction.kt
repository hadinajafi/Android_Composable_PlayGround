package com.example.moneymanager.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneymanager.models.TransactionDto
import java.sql.Timestamp

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(index = true)
    val uuid: String,
    val title: String,
    val description: String? = null,
    val amount: Float,
    val createdAt: Long,
    val updatedAt: Long
) {

    fun toInternal(): TransactionDto {
        return TransactionDto(
            uuid = this.uuid,
            title = this.title,
            description = this.description,
            amount = this.amount,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
        )
    }

    companion object {
        fun fromInternal(internal: TransactionDto): Transaction {
            return Transaction(
                uuid = internal.uuid,
                title = internal.title,
                description = internal.description,
                amount = internal.amount,
                createdAt = internal.createdAt,
                updatedAt = internal.updatedAt,
            )
        }
    }
}