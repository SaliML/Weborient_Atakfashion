package com.weborient.inventory.ui.out

import com.weborient.inventory.repositories.item.ItemRepository

class OutInteractor(private val presenter: IOutContract.IOutPresenter): IOutContract.IOutInteractor {
    override fun decreaseAmount(amount: Int) {
        presenter.onResultDecreaseAmount(true)
    }

    override fun getItemByID(itemID: String) {
        presenter.onFetchedItem(ItemRepository.testItem)
    }
}