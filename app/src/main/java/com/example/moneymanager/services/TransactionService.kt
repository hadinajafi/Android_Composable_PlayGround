package com.example.moneymanager.services

import android.content.Context
import com.example.moneymanager.MoneyManager
import com.example.moneymanager.db.entities.Transaction
import com.example.moneymanager.db.repositories.AppDatabase
import com.example.moneymanager.models.TransactionDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture
import kotlinx.coroutines.launch

class TransactionService(context: Context) {

    private val database: AppDatabase

    init {
        database = AppDatabase.getInstance(context)
    }

    fun getAllTransactions(): List<TransactionDto> {
        return CoroutineScope(Dispatchers.IO).async {
            database.transactionsDao().getAllTransactions()
        }.asCompletableFuture().get().map { it.toInternal() }
    }

    fun saveTransaction(transaction: TransactionDto) {
        CoroutineScope(Dispatchers.IO).launch {
            database.transactionsDao()
                .insertTransaction(Transaction.fromInternal(transaction))
        }
    }
}