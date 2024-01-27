package com.example.moneymanager.services

import com.example.moneymanager.models.TransactionDto

class TransactionService {
    private val transactionDtos: MutableList<TransactionDto> = mutableListOf()

    fun getTransactions(): List<TransactionDto> {
        return transactionDtos
    }

    fun addTransaction(transactionDto: TransactionDto) {
        transactionDtos.add(transactionDto)
    }
}