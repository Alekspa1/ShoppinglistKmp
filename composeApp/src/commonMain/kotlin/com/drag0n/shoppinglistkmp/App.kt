package com.drag0n.shoppinglistkmp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.drag0n.shoppinglistkmp.Const.ITEM
import com.drag0n.shoppinglistkmp.Const.LIST
import com.drag0n.shoppinglistkmp.Const.SETTINGS
import com.drag0n.shoppinglistkmp.domain.ShopItems
import com.drag0n.shoppinglistkmp.domain.ShopingList
import com.drag0n.shoppinglistkmp.presentaton.AddItemsDialog
import com.drag0n.shoppinglistkmp.presentaton.ShopingList
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App(viewModel: MyViewModel = koinViewModel()) {

    var showDialog by remember { mutableStateOf(false) }
    var who by remember { mutableStateOf("") }
    val openId by viewModel.expandedId.collectAsStateWithLifecycle()
    val shopList by viewModel.shopListFlow.collectAsStateWithLifecycle()
    val itemList by viewModel.itemsListFlow.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showDialog = true
                    who = LIST
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить"
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Список покупок") },
                actions = {
                    IconButton(onClick = {
                        who = SETTINGS
                        showDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Настройки"
                        )
                    }
                }
            )
        }
    )
    { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ShopingList(
               listShop =  shopList,
                openId = openId,
                onExpandClick = { id->
                    viewModel.toggleExpand(id)},
                itemList
                ){
                state->
                who = ITEM
                showDialog = state
            }
        }
    }

    if (showDialog) {
        AddItemsDialog(
            who = who,
            onDismiss = { showDialog = false },
            onConfirm = { text,count ->
                when(who){
                        SETTINGS->{viewModel.initKey(text)}
                        ITEM->{viewModel.addItemsShopListFun(text,count.toInt())}
                        LIST ->{viewModel.createShopList(text)}
                }
                showDialog = false
            },
            key = viewModel.myKey()
        )
    }


}