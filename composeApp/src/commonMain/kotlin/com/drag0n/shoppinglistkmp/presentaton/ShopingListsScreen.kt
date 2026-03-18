package com.drag0n.shoppinglistkmp.presentaton

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.drag0n.shoppinglistkmp.domain.ShopItems
import com.drag0n.shoppinglistkmp.domain.ShopingList
import kotlin.time.Duration.Companion.days


@Composable
fun ShopingList(listShop: List<ShopingList>) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(

            modifier = Modifier.fillMaxSize(),


            verticalArrangement = Arrangement.spacedBy(12.dp),


            contentPadding = PaddingValues(8.dp)

        ) {
            items(items = listShop, key = { it.id }) { shop ->

                // 1. Заголовок (название списка покупок)
                Text(text = shop.name, color = Color.Black)

                // 2. Вложенные элементы (товары внутри этого списка)
                shop.items.forEach { shopItem ->
                    ItemWeather(shopItem)
                }
            }
        }


        }
    }




@Composable
fun ItemWeather(item: ShopItems) {

    var isExpanded by remember { mutableStateOf(false) }

    Column(

        modifier = Modifier

            .fillMaxWidth()


    ) {


        Card(

            modifier = Modifier

                .fillMaxWidth()

                .clip(RoundedCornerShape(16.dp))

                .clickable { isExpanded = !isExpanded },

            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x80ADD8E6)
            ),

            elevation = CardDefaults.cardElevation(0.dp)

        ) {

            Column(modifier = Modifier.padding(12.dp)) {

                Text(

                    text = item.name,

                    modifier = Modifier.fillMaxWidth(),

                    textAlign = TextAlign.End,

                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White

                )

                Row(

                    modifier = Modifier.fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically

                ) {

                    Text(
                        item.name, style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )


                    Spacer(modifier = Modifier.weight(1f))



                    Text(

                        text = item.name,

                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White

                    )

                }

            }

        }


        // Второстепенная карточка

        AnimatedVisibility(

            visible = isExpanded,

            enter = expandVertically() + fadeIn(),

            exit = shrinkVertically() + fadeOut()

        ) {

            Card(

                modifier = Modifier

                    .fillMaxWidth()

                    .padding(top = 8.dp),

                shape = RoundedCornerShape(16.dp),

                colors = CardDefaults.cardColors(
                    containerColor = Color(0x80ADD8E6)
                ),

                elevation = CardDefaults.cardElevation(0.dp)

            ) {

                Column(
                    modifier = Modifier.padding(16.dp),

                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(

                        text = item.name,
                        color = Color.White


                    )

                    Text(

                        text = item.name,
                        color = Color.White


                    )

                    Text(

                        text = item.name,
                        color = Color.White


                    )

                    Text(

                        text = item.name,
                        color = Color.White


                    )


                }


            }

        }

    }

}
