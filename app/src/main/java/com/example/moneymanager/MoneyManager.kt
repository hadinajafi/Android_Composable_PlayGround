package com.example.moneymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.room.Room
import com.example.moneymanager.db.repositories.AppDatabase
import com.example.moneymanager.ui.home.OverviewScreen

class MoneyManager : ComponentActivity() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "money_manager_db"
        ).build()

        setContent {
            MaterialTheme {
                OverviewScreen()
            }
        }
    }
}