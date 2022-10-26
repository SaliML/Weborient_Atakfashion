package com.weborient.inventory.ui.`in`

import com.weborient.inventory.models.ItemModel

class InPresenter(private val view: IInContract.IInView): IInContract.IInPresenter {
    private val interactor = InInteractor(this)

    override fun getItems() {
        interactor.getItems()
    }

    override fun onFetchedItems(itemList: ArrayList<ItemModel>) {
        view.showItems(itemList)
    }

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun onClickedAddButton() {
        view.showAddNewItemFragment()
    }
}