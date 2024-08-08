package com.example.moneymanager.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.moneymanager.models.TransactionDto
import com.example.moneymanager.services.TransactionService
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewScreen(
    onAddTransactionClick: () -> Unit = {},
    transactionService: TransactionService
) {
    val state = remember {
        mutableStateMapOf<String, TransactionDto>()
    }
    transactionService.getAllTransactions().map {
        state.put(it.uuid, it)
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
            transactions = state,
            transactionService = transactionService
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun TransactionContent(
    transactions: SnapshotStateMap<String, TransactionDto>,
    modifier: Modifier = Modifier,
    transactionService: TransactionService,
) {
    LazyColumn(modifier = modifier) {
        // TODO: rewrite this with items function
        //todo: use sticky header to separate dates
        transactions.forEach {
            item {
                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth()
                ) {
                    var openDeleteDialog by remember { mutableStateOf(false) }

                    if (openDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { openDeleteDialog = false },
                            confirmButton = {
                                TextButton(onClick = { openDeleteDialog = false }) {
                                    Text(text = "Cancel")
                                }
                                TextButton(onClick = {
                                    openDeleteDialog = false
                                    transactionService.deleteOne(it.value.uuid)
                                    transactions.remove(it.key)
                                }) {
                                    Text(text = "Delete")
                                }
                            },
                            title = { Text(text = "Confirm Delete") },
                            text = { Text(text = "Are you sure you want to delete transaction: ${it.value.title} ?") },
                            icon = { Icons.Default.Delete })
                    }

                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp, start = 2.dp, top = 2.dp, end = 2.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(onLongPress = { openDeleteDialog = true })
                            },
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = it.value.title,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .width(170.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = TextUnit(18F, TextUnitType.Sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    text = LocalDateTime.ofInstant(
                                        Instant.ofEpochSecond(it.value.updatedAt),
                                        ZoneId.of("UTC")
                                    ).format(
                                        DateTimeFormatter.ISO_ORDINAL_DATE
                                    ) + " - " + LocalDateTime.ofInstant(
                                        Instant.ofEpochSecond(it.value.updatedAt), //todo: apply the designs to the actual code
                                        ZoneId.of("UTC")
                                    ).format(
                                        DateTimeFormatter.ISO_TIME
                                    ), modifier = Modifier.padding(20.dp)
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = it.value.description ?: "",
                                    modifier = Modifier
                                        .padding(start = 20.dp, bottom = 10.dp, end = 10.dp)
                                        .width(250.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = it.value.amount.toString(),
                                    modifier = Modifier.wrapContentWidth(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun OverviewScreenPreview() {

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
                    IconButton(onClick = { }) {
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
        TransactionContentPreview(
            modifier = Modifier
                .fillMaxWidth()
                .padding(it),
        )
    }
}

@Composable
@Preview
private fun TransactionContentPreview(
    modifier: Modifier = Modifier,
    transactions: List<TransactionDto> = listOf(
        TransactionDto(
            title = "Title",
            description = "Something",
            amount = 20F,
            createdAt = Instant.now().epochSecond,
            updatedAt = Instant.now().epochSecond
        ),
        TransactionDto(
            title = "A very very very very long title that I could Title",
            description = "A very very very very long description that I could think of to break the UI",
            amount = 20F,
            createdAt = Instant.now().epochSecond,
            updatedAt = Instant.now().epochSecond
        )
    )
) {
    LazyColumn(modifier = modifier) {
        transactions.forEach {
            item {
                Column(
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth()
                ) {
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 2.dp, start = 2.dp, top = 2.dp, end = 2.dp),
                        elevation = CardDefaults.cardElevation(1.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = it.title,
                                    modifier = Modifier
                                        .padding(20.dp)
                                        .width(170.dp),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = TextUnit(18F, TextUnitType.Sp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                                Text(
                                    text = LocalDateTime.ofInstant(
                                        Instant.ofEpochSecond(it.updatedAt),
                                        ZoneId.of("UTC")
                                    ).format(
                                        DateTimeFormatter.ISO_ORDINAL_DATE
                                    ) + " - " + LocalDateTime.ofInstant(
                                        Instant.ofEpochSecond(it.updatedAt), //todo: apply the designs to the actual code
                                        ZoneId.of("UTC")
                                    ).format(
                                        DateTimeFormatter.ISO_TIME
                                    ), modifier = Modifier.padding(20.dp)
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = it.description ?: "",
                                    modifier = Modifier
                                        .padding(start = 20.dp, bottom = 10.dp, end = 10.dp)
                                        .width(250.dp),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = it.amount.toString(),
                                    modifier = Modifier.wrapContentWidth(Alignment.End)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DeleteAlertPreview() {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = {}) {
                Text(text = "Cancel")
            }
            TextButton(onClick = {

            }) {
                Text(text = "Delete")
            }
        },
        title = { Text(text = "Confirm Delete") },
        text = { Text(text = "Are you sure you want to delete transaction: TransactionTitle ?") },
        icon = { Icons.Default.Delete })
}
