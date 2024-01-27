package com.example.moneymanager.db.repositories

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moneymanager.db.entities.Transaction

@Database(entities = [Transaction::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun transactionDao(): Transaction
}