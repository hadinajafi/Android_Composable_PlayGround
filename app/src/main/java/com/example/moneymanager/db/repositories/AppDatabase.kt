package com.example.moneymanager.db.repositories

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.moneymanager.db.entities.Transaction


private const val dbName: String = "money_manager_db"
private var instance: AppDatabase? = null

@Database(entities = [Transaction::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun getInstance(context: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    dbName
                )
                    .fallbackToDestructiveMigration()
                    .build()
            return instance as AppDatabase
        }
    }

    abstract fun transactionsDao(): TransactionsDao
}