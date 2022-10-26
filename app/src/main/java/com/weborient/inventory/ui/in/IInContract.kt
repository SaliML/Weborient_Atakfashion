package com.weborient.inventory.ui.`in`

import com.weborient.inventory.models.ItemModel

/**
 * MVP minta a főoldalra
 */
interface IInContract {
    interface IInView{
        fun showAddNewItemFragment()
        fun showItems(itemList: ArrayList<ItemModel>)
        fun closeActivity()
    }

    interface IInPresenter{
        fun getItems()
        fun onFetchedItems(itemList: ArrayList<ItemModel>)
        fun onClickedBackButton()
        fun onClickedAddButton()
    }

    interface IInInteractor{
        fun getItems()
    }
}