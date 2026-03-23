package com.drag0n.shoppinglistkmp

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.drag0n.shoppinglistkmp.domain.ShopItems
import com.drag0n.shoppinglistkmp.domain.ShopingList
import com.drag0n.shoppinglistkmp.presentaton.ShopingList
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App(viewModel: MyViewModel = koinViewModel()){
    val listShop = mutableListOf<ShopingList>()
    for(i in 0..5){
        val listItems = mutableListOf<ShopItems>()
        for(j in 0..10){
            listItems.add(ShopItems(j.toLong(),"Товар: $j"))
        }
        listShop.add(ShopingList(i.toLong(), "Список: $i", listItems))
    }
ShopingList(listShop)

}