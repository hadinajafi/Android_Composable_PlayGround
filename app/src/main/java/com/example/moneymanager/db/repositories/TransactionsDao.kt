package com.example.moneymanager.db.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneymanager.db.entities.Transaction

@Dao
interface TransactionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: Transaction)

    @Query("SELECT * FROM `transactions` LIMIT 50")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("DELETE FROM `transactions` WHERE uuid = :uuid")
    suspend fun deleteOne(uuid: String)
}