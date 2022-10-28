package com.weborient.inventory.ui.newitem

import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.models.ItemModel

/**
 * MVP minta az új termék felülethez
 */
interface INewItemContract {
    interface INewItemView{
        fun setItemID(id: String)
        fun setCategories(categories: ArrayList<String>)
        fun setPresentations(presentation: ArrayList<String>)
        fun setUnits(units: ArrayList<String>)
        fun setStatuses(statuses: ArrayList<String>)
        fun setTemplates(templates: ArrayList<String>)
        fun setTaxes(taxes: ArrayList<String>)
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showNameError(error: String?)
        fun showDescriptionError(error: String?)
        fun showQuantityError(error: String?)
        fun showCategoryError(error: String?)
        fun showPresentationError(error: String?)
        fun showUnitError(error: String?)
        fun showStatusError(error: String?)
        fun showTaxError(error: String?)
        fun showNetPriceError(error: String?)
        fun showTemplateError(error: String?)
        fun showMarginError(error: String?)
        fun showPrintButton()
        fun closeFragment()
    }

    interface INewItemPresenter{
        fun onClickedBackButton()
        fun onClickedUploadButton(name: String?, description: String?, quantity: String?, category: String?, presentation: String?, unit: String?, status: String?, template: String?, tax: String?, netPrice: String?, margin: String?)
        fun onClickedPrintButton(quantity: String)
        fun onUploadedResult(isSuccessful: Boolean, id: String?)
        fun onRetrievedCategories(category: ArrayList<String>)
        fun onRetrievedPresentations(presentation: ArrayList<String>)
        fun onRetrievedUnits(units: ArrayList<String>)
        fun onRetrievedStatuses(statuses: ArrayList<String>)
        fun onRetrievedTemplates(templates: ArrayList<String>)
        fun onRetrievedTaxes(taxes: ArrayList<String>)
    }

    interface INewItemInteractor{
        fun uploadItem(item: ItemModel, quantity: Int)
        fun getCategories()
        fun getPresentation()
        fun getUnits()
        fun getStatuses()
        fun getTemplates()
        fun getTaxes()
    }
}