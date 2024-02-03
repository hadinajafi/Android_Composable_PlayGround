package com.example.moneymanager

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.moneymanager.services.TransactionService
import com.example.moneymanager.ui.home.OverviewScreen
import com.example.moneymanager.ui.theme.MoneyManagerTheme
import com.example.moneymanager.ui.transactions.AddTransaction

class MoneyManager : ComponentActivity() {

    private lateinit var transactionService: TransactionService

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transactionService = TransactionService(this)

        setContent {
            MoneyManagerTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "overviewScreen") {
                    composable("overviewScreen") {
                        OverviewScreen(onAddTransactionClick = {
                            navController.navigate("addTransaction")
                        }, transactions = transactionService.getAllTransactions())
                    }
                    composable("addTransaction") {
                        AddTransaction(transactionService, navController)
                    }
                }
            }
        }
    }
}
