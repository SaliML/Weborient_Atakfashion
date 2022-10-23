package com.weborient.inventory.repositories.item

import com.weborient.inventory.models.ItemModel

object ItemRepository {
    var items: ArrayList<ItemModel> = arrayListOf(
        ItemModel("1"),
        ItemModel("2")
    )
    var selectedItem: ItemModel? = null
}