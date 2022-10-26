package com.weborient.inventory.ui.`in`

import com.weborient.inventory.repositories.item.ItemRepository

class InInteractor(private val presenter: IInContract.IInPresenter): IInContract.IInInteractor {
    override fun getItems() {
        presenter.onFetchedItems(ItemRepository.testItems)
    }
}