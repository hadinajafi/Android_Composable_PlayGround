package com.example.moneymanager.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moneymanager.models.TransactionDto
import java.util.UUID

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    @ColumnInfo(index = true)
    val uuid: String,
    val title: String,
    val description: String? = null,
    val amount: Float,
) {

    fun toInternal(): TransactionDto {
        return TransactionDto(
            uuid = this.uuid,
            title = this.title,
            description = this.description,
            amount = this.amount,
        )
    }

    companion object {
        fun fromInternal(transaction: TransactionDto): Transaction {
            return Transaction(
                uuid = transaction.uuid,
                title = transaction.title,
                description = transaction.description,
                amount = transaction.amount,
            )
        }
    }
}