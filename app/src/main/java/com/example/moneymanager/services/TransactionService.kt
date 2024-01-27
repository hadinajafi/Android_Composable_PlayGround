package com.example.moneymanager.services

import com.example.moneymanager.models.Transaction

class TransactionService {
    private val transactions: MutableList<Transaction> = mutableListOf()

    fun getTransactions(): List<Transaction> {
        return transactions
    }

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }
}