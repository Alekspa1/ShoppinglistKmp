package com.drag0n.shoppinglistkmp.presentaton

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.drag0n.shoppinglistkmp.Const.ITEM
import com.drag0n.shoppinglistkmp.Const.LIST
import com.drag0n.shoppinglistkmp.Const.SETTINGS


@Composable
fun AddItemsDialog(
    onDismiss: () -> Unit,
    who: String,
    onConfirm: (String,String) -> Unit,
    key: String,
) {
    val textTitle = when(who){
            LIST -> "Создать новый список покупок"
            ITEM -> "Добавить элемент в корзину"
            SETTINGS -> "Ваш ключ: $key"
            else -> ""
    }
    var text by remember { mutableStateOf("") }
    var count by remember { mutableStateOf("1") }
    val focusRequester = remember { FocusRequester() }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = { Icon(Icons.Default.LocationCity, contentDescription = null) },
        title = { Text(text = textTitle) },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Введите название") },
                    singleLine = true,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .focusRequester(focusRequester)
                )
                if (who == ITEM){
                    OutlinedTextField(
                        value = count,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                count = newValue
                            }
                        },
                        label = { Text("Введите количество") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            }

        },
        confirmButton = {
            Button(
                enabled = text.isNotBlank(),
                onClick = { onConfirm(text,count) }
            ) {
                Text("ОК")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}