package com.weborient.inventory.ui.`in`

import com.weborient.inventory.models.ItemModel

interface IItemClickHandler {
    fun onClickedItem(item: ItemModel?)
}