package com.example.moneymanager.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.moneymanager.models.TransactionDto
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
                    IconButton(onClick = {
                        //TODO: fix it with navigation
                    }) {
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
            transactionDtos = listOf(
                TransactionDto(UUID.randomUUID(), "Title", "description", 203.34F),
                TransactionDto(UUID.randomUUID(), "Title 2", "desc", 20.21F)
            )
        )
    }
}

@Composable
private fun TransactionContent(
    transactionDtos: List<TransactionDto>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        transactionDtos.forEach {
            item {
                TransactionItem(transactionDto = it)
            }
        }
    }
}

@Composable
fun TransactionItem(
    transactionDto: TransactionDto
) {
    Column(
        modifier = Modifier
            .padding()
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dp(2F), start = Dp(2F), top = Dp(2F), end = Dp(2F)),
            border = BorderStroke(width = Dp(2F), color = Color.Gray),
            shape = CardDefaults.elevatedShape,
        ) {
            Text(text = transactionDto.title, Modifier.padding(Dp(20F)))
        }

    }
}