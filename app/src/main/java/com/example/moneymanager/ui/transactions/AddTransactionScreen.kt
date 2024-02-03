package com.example.moneymanager.ui.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.moneymanager.models.TransactionDto
import com.example.moneymanager.services.TransactionService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransaction(transactionService: TransactionService, navController: NavController) {
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
                .fillMaxWidth(),
            transactionService,
            navController
        )
    }
}

@Composable
private fun AddTransactionMeta(
    modifier: Modifier = Modifier,
    transactionService: TransactionService,
    navController: NavController
) {
    val textFieldModifier = Modifier
        .padding(bottom = 2.dp)
        .fillMaxWidth()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
    ) {

        fun isFloat(text: String): Boolean {
            isError = text.toFloatOrNull() == null
            return text.toFloatOrNull() != null
        }

        OutlinedTextField(
            modifier = textFieldModifier,
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "Title") },
            textStyle = MaterialTheme.typography.titleMedium
        )
        OutlinedTextField(
            modifier = textFieldModifier,
            value = description,
            onValueChange = { description = it },
            label = { Text(text = "Description") })
        OutlinedTextField(
            modifier = textFieldModifier,
            onValueChange = { if (isFloat(it)) amount = it },
            value = amount,
            label = { Text(text = "Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError,

            )
        Button(
            modifier = Modifier
                .padding(bottom = 2.dp)
                .fillMaxWidth(),
            shape = Shapes().extraSmall,
            onClick = {
                if (isError) return@Button
                transactionService.saveTransaction(
                    TransactionDto(
                        title = title,
                        description = description,
                        amount = amount.toFloat()
                    )
                )
                navController.popBackStack()
            }) {
            Text(text = "Save")
        }

        //todo: add cancel button and back button to the header
    }
}
