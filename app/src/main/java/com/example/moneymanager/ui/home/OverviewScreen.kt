package com.example.moneymanager.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moneymanager.models.Transaction
import java.util.UUID


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun OverviewScreen() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Today's Transactions") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add new transaction"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        TransactionContent(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            transactions = listOf(
                Transaction(UUID.randomUUID(), "Title", "description", 203.34F),
                Transaction(UUID.randomUUID(), "Title 2", "desc", 20.21F)
            )
        )
    }
}

@Composable
private fun TransactionContent(
    transactions: List<Transaction>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        transactions.forEach {
            item {
                TransactionItem(transaction = it)
            }
        }
    }
}

@Composable
fun TransactionItem(
    transaction: Transaction
) {
    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
    ) {
        Text(text = transaction.title)
    }
}