package com.weborient.inventory.ui.newitem

import com.weborient.inventory.models.ItemModel

class NewItemInteractor(private val presenter: INewItemContract.INewItemPresenter): INewItemContract.INewItemInteractor {
    override fun uploadItem(item: ItemModel, quantity: Int) {
        TODO("Not yet implemented")
    }

    override fun getCategories() {
        TODO("Not yet implemented")
    }

    override fun getPresentation() {
        TODO("Not yet implemented")
    }

    override fun getUnits() {
        TODO("Not yet implemented")
    }

    override fun getStatuses() {
        TODO("Not yet implemented")
    }

    override fun getTemplates() {
        TODO("Not yet implemented")
    }

    override fun getTaxes() {
        TODO("Not yet implemented")
    }

}