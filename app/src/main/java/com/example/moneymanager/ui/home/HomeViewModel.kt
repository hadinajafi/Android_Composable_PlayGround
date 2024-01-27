package com.example.moneymanager.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moneymanager.models.Transaction

class HomeViewModel : ViewModel() {

    val transactions = mutableListOf(
        Transaction(
            title = "First transaction",
            description = null,
            amount = 4.3F
        )
    )

}