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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.drag0n.shoppinglistkmp.Const.CROSS_IT
import com.drag0n.shoppinglistkmp.Const.ITEM
import com.drag0n.shoppinglistkmp.Const.LIST
import com.drag0n.shoppinglistkmp.Const.UPDATES
import com.drag0n.shoppinglistkmp.domain.model.Shop
import com.drag0n.shoppinglistkmp.domain.model.items.Item


@Composable
fun ShopingList(
    listShop: List<Shop>,
    openId: Int?,
    onExpandClick: (Int?) -> Unit,
    itemList: List<Item>,
    onShowDialogChange: (Int, Boolean, String) -> Unit,
    onDeleteButtonOrChangeItem: (Int, String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(

            modifier = Modifier.fillMaxSize(),


            verticalArrangement = Arrangement.spacedBy(12.dp),


            contentPadding = PaddingValues(8.dp)

        ) {

            items(items = listShop, key = { item -> item.id }) { shop ->
                Item(
                    item = shop,
                    isExpanded = shop.id == openId,
                    onExpandClick = { onExpandClick(shop.id) },
                    onShowDialogChange = onShowDialogChange,
                    itemsList = itemList,
                    onDeleteButtonOrChangeItem = onDeleteButtonOrChangeItem

                )
            }
        }


    }
}


@Composable
fun Item(
    item: Shop,
    onShowDialogChange: (Int, Boolean, String) -> Unit,
    isExpanded: Boolean,
    onExpandClick: () -> Unit,
    itemsList: List<Item>,
    onDeleteButtonOrChangeItem: (Int, String) -> Unit
) {


    Column(

        modifier = Modifier

            .fillMaxWidth()


    ) {


        Card(

            modifier = Modifier

                .fillMaxWidth()

                .clip(RoundedCornerShape(16.dp))

                .clickable { onExpandClick() },

            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x80ADD8E6)
            ),

            elevation = CardDefaults.cardElevation(0.dp)

        ) {
            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                verticalAlignment = Alignment.CenterVertically

            ) {

                Text(
                    item.name, style = MaterialTheme.typography.titleSmall,
                    color = Color.Blue
                )


                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { onDeleteButtonOrChangeItem(item.id, LIST) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Удалить элемент"
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
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsList.forEach {


                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = it.name,
                                style = TextStyle(
                                    textDecoration = if (it.isCrossed) TextDecoration.LineThrough else TextDecoration.None,

                                    color = if (it.isCrossed) Color.Gray else Color.Red
                                ),
                                modifier = Modifier.clickable {
                                    onShowDialogChange(
                                        it.id,
                                        true,
                                        UPDATES
                                    )
                                }
                            )
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = it.n.toString(),
                                style = TextStyle(
                                    textDecoration = if (it.isCrossed) TextDecoration.LineThrough else TextDecoration.None,

                                    color = if (it.isCrossed) Color.Gray else Color.Red
                                )
                            )

                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { onDeleteButtonOrChangeItem(it.id, CROSS_IT) }) {
                                Icon(
                                    imageVector = if (it.isCrossed) Icons.Default.CheckBox else Icons.Default.CheckBoxOutlineBlank,
                                    contentDescription = null,
                                    tint = if (it.isCrossed) Color.Blue else Color.White
                                )
                            }
                            IconButton(onClick = { onDeleteButtonOrChangeItem(it.id, ITEM) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить элемент"
                                )
                            }

                        }

                    }

                    IconButton(
                        modifier = Modifier.align(Alignment.End),
                        onClick = { onShowDialogChange(0, true, ITEM) })
                    {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Добавить элемент",
                        )
                    }
                }


            }

        }

    }

}
