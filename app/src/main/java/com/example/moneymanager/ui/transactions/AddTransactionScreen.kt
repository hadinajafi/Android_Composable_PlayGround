package com.example.moneymanager.ui.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.moneymanager.MoneyManager
import com.example.moneymanager.db.entities.Transaction
import com.example.moneymanager.models.TransactionDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun AddTransaction() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Add New Transaction") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) {
        AddTransactionMeta(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
        )
    }
}

@Composable
private fun AddTransactionMeta(
    modifier: Modifier = Modifier
) {
    val textFieldModifier = Modifier
        .padding(bottom = Dp(2F))
        .fillMaxWidth()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableFloatStateOf(0.0F) }
    Column(
        modifier = modifier
            .padding(all = Dp(4F))
            .fillMaxWidth()
    ) {
        TextField(
            modifier = textFieldModifier,
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") })
        TextField(
            modifier = textFieldModifier,
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") })
        TextField(
            modifier = textFieldModifier,
            onValueChange = { amount = it.toFloatOrNull() ?: amount },
            value = amount.toString(),
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
        Button(
            modifier = textFieldModifier,
            shape = Shapes().extraSmall,
            onClick = {
                saveTransaction(
                    TransactionDto(
                        title = title,
                        description = description,
                        amount = amount
                    )
                )
            }) {
            Text(text = "Save")
        }

        //todo: add cancel button and back button to the header
    }
}

private fun saveTransaction(transaction: TransactionDto) {
    CoroutineScope(Dispatchers.IO).launch {
        MoneyManager.database.transactionsDao()
            .insertTransaction(Transaction.fromInternal(transaction))
    }
}