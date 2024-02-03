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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.moneymanager.models.TransactionDto
import com.example.moneymanager.services.TransactionService


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onAddTransactionClick: () -> Unit = {},
    transactionService: TransactionService
) {
    val transactionsState = remember { mutableStateOf(transactionService.getAllTransactions()) }

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
            transactions = transactionsState.value,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun TransactionContent(
    transactions: List<TransactionDto>,
    modifier: Modifier = Modifier,
) {
    println(transactions.size)
    LazyColumn(modifier = modifier) {
        transactions.forEach {
            item {
                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth()
                ) {
//                    val deleteSwipeState = rememberDismissState()
//
//                    if (deleteSwipeState.isDismissed(DismissDirection.EndToStart)) {
//                        transactionService.deleteOne(it.uuid)
//                        println("delete function called")
////                        transactions.remove(it)
//                    }

//                    SwipeToDismiss(
//                        state = deleteSwipeState,
//                        directions = setOf(DismissDirection.EndToStart),
//                        background = {
//                            val color =
//                                when {
//                                    deleteSwipeState.targetValue.equals(DismissValue.DismissedToStart) -> {
//                                        Color.Red
//                                    }
//                                    else -> {
//                                        Color.White
//                                    }
//                                }
//                            val alignment = Alignment.CenterEnd
//                            val icon = Icons.Default.Delete
//
//                            val scale by animateFloatAsState(
//                                if (deleteSwipeState.targetValue == DismissValue.DismissedToStart) 0.75f else 1f,
//                                label = ""
//                            )
//
//                            Box(
//                                Modifier
//                                    .fillMaxSize()
//                                    .background(color)
//                                    .padding(horizontal = Dp(20f)),
//                                contentAlignment = alignment
//                            ) {
//                                Icon(
//                                    icon,
//                                    contentDescription = "Delete Icon",
//                                    modifier = Modifier.scale(scale)
//                                )
//                            }
//                        },
//                        dismissContent = {
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(
//                                        bottom = Dp(2F),
//                                        start = Dp(2F),
//                                        top = Dp(2F),
//                                        end = Dp(2F)
//                                    ),
//                                border = BorderStroke(width = Dp(2F), color = Color.Gray),
//                                shape = CardDefaults.elevatedShape,
//                            ) {
//
//                                Text(
//                                    text = it.title,
//                                    modifier = Modifier.padding(Dp(20F)),
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = TextUnit(18F, TextUnitType.Sp)
//                                )
//                                Text(
//                                    text = it.description ?: "",
//                                    Modifier.padding(start = 20.dp, bottom = 10.dp)
//                                )
//                            }
//                        }
//                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Dp(2F), start = Dp(2F), top = Dp(2F), end = Dp(2F)),
                        border = BorderStroke(width = Dp(2F), color = Color.Gray),
                        shape = CardDefaults.elevatedShape,
                    ) {

                        Text(
                            text = it.title,
                            modifier = Modifier.padding(Dp(20F)),
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit(18F, TextUnitType.Sp)
                        )
                        Text(
                            text = it.description ?: "",
                            Modifier.padding(start = 20.dp, bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}
