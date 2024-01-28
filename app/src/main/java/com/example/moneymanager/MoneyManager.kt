package com.example.moneymanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.moneymanager.db.repositories.AppDatabase
import com.example.moneymanager.ui.home.OverviewScreen
import com.example.moneymanager.ui.theme.MoneyManagerTheme
import com.example.moneymanager.ui.transactions.AddTransaction

class MoneyManager : ComponentActivity() {

    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = Room.databaseBuilder(
            this.applicationContext,
            AppDatabase::class.java,
            "money_manager_db"
        ).build()

        setContent {
            MoneyManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "overviewScreen") {
                    composable("overviewScreen") {
                        OverviewScreen(onAddTransactionClick = {
                            navController.navigate("addTransaction")
                        })
                    }
                    composable("addTransaction") {
                        AddTransaction()
                    }
                }
            }
        }
    }
}


/*
NavHost(navController, startDestination = "home") {
    composable("home") {
        HomeScreen(onDetailsClick = { id ->
            navController.navigate("details/id=$id?name=hi")
        }, onAboutClick = {
            navController.navigate("about")
        })
    }
    composable("about") {
        AboutScreen(onNavigateUp = {
            navController.popBackStack()
        })
    }
    composable(
        "details/id={id}?name={name}",
        arguments = listOf(navArgument("id") {
            type = NavType.LongType
        }, navArgument("name") {
            type = NavType.StringType
            nullable = true
        }),
    ) { backStackEntry ->
        val arguments = requireNotNull(backStackEntry.arguments)
        val id = arguments.getLong("id")
        val name = arguments.getString("name")
        DetailsScreen(id, name, onNavigateUp = {
            navController.popBackStack()
        })
    }
}

 */