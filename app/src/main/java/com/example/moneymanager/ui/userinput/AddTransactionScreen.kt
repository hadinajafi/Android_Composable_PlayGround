package com.example.moneymanager.ui.userinput

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

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
    var title = ""
    var description: String?
    var amount: Float = 0.0F
    Column(
        modifier = modifier
            .padding(all = Dp(4F))
            .fillMaxWidth()
    ) {
        TextField(
            modifier = textFieldModifier,
            value = "",
            onValueChange = { description = it },
            label = { Text(text = "Title") })
        TextField(
            modifier = textFieldModifier,
            value = "",
            onValueChange = { description = it },
            label = { Text(text = "Description") })
        TextField(
            modifier = textFieldModifier,
            onValueChange = { amount = it.toFloatOrNull() ?: 0.0F },
            value = "",
            label = { Text(text = "0.0") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
        Button(
            modifier = textFieldModifier,
            shape = Shapes().extraSmall,
            onClick = {
            }) {
            Text(text = "Save")
        }
    }
}