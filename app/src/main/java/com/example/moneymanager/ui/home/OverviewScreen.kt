package com.example.moneymanager.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.moneymanager.MoneyManager
import com.example.moneymanager.models.TransactionDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.future.asCompletableFuture


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onAddTransactionClick: () -> Unit = {},
) {
    val transactions = remember {
        mutableStateOf(fetchTransactions())
    }
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
                    IconButton(onClick = onAddTransactionClick) {
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
                .fillMaxWidth()
                .padding(it),
            transactionDtos = transactions.value
        )
    }
}

private fun fetchTransactions(): List<TransactionDto> {
    return CoroutineScope(Dispatchers.IO).async {
        MoneyManager.database.transactionsDao().getAllTransactions()
    }.asCompletableFuture().get().map { it.toInternal() }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun TransactionContent(
    transactionDtos: List<TransactionDto>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        println("transactionSize: " + transactionDtos.size)
        transactionDtos.forEach {
            item {
                TransactionItem(transactionDto = it)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
@Preview
fun TransactionItem(
    @PreviewParameter(TransactionPreviewProvider::class) transactionDto: TransactionDto
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

            Text(
                text = transactionDto.title,
                modifier = Modifier.padding(Dp(20F)),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(18F, TextUnitType.Sp)
            )
            Text(
                text = transactionDto.description ?: "",
                Modifier.padding(start = 20.dp, bottom = 10.dp)
            )
        }
    }
}

class TransactionPreviewProvider : PreviewParameterProvider<TransactionDto> {
    override val values = sequenceOf(
        TransactionDto(title = "Title", description = "Something", amount = 20F)
    )
}