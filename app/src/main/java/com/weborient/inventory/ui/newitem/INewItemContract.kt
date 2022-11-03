package com.weborient.inventory.ui.newitem

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.models.ItemModel
import kotlinx.coroutines.GlobalScope

/**
 * MVP minta az új termék felülethez
 */
interface INewItemContract {
    interface INewItemView{
        fun setItemID(id: String?)
        fun setCategories(categories: ArrayList<String>)
        fun setPresentations(presentations: ArrayList<String>)
        fun setUnits(units: ArrayList<String>)
        fun setStatuses(statuses: ArrayList<String>)
        fun setTemplates(templates: ArrayList<String>)
        fun setTaxes(taxes: ArrayList<String>)
        fun setGrossPrice(price: String)
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showNameError(error: String?)
        fun showDescriptionError(error: String?)
        fun showQuantityError(error: String?)
        fun showCategoryError(error: String?)
        fun showPresentationError(error: String?)
        fun showUnitError(error: String?)
        fun showStatusError(error: String?)
        fun showTemplateError(error: String?)
        fun showGrossPriceError(error: String?)
        fun showPrintButton()
        fun showBluetoothDialog()
        fun showNetworkDialog()
        fun showProgress(information: String)
        fun hideProgress()
        fun closeFragment()
    }

    interface INewItemPresenter{
        fun getCategories()
        fun getPresentation()
        fun getUnits()
        fun getStatuses()
        fun getTemplates()
        fun getTaxes()
        fun countGrossPrice(netPrice: String?, tax: String?, margin: String?)
        fun onClickedBackButton()
        fun onClickedUploadButton(name: String?, description: String?, quantity: String?, category: String?, presentation: String?, unit: String?, status: String?, template: String?, grossPrice: String?)
        fun onClickedPrintButton(id: String, quantity: String?, bluetoothAdapter: BluetoothAdapter?)
        fun onUploadedResult(isSuccessful: Boolean, id: String?)
        fun onRetrievedCategories(categories: ArrayList<String>)
        fun onRetrievedPresentations(presentations: ArrayList<String>)
        fun onRetrievedUnits(units: ArrayList<String>)
        fun onRetrievedStatuses(statuses: ArrayList<String>)
        fun onRetrievedTemplates(templates: ArrayList<String>)
        fun onRetrievedTaxes(taxes: ArrayList<String>)
        fun onDialogResult(result: DialogResultEnums)
        fun onPrintResult(result: PrintResult)
    }

    interface INewItemInteractor{
        fun uploadItem(item: ItemModel, quantity: Int)
        fun getCategories()
        fun getPresentation()
        fun getUnits()
        fun getStatuses()
        fun getTemplates()
        fun getTaxes()
        fun print(id: String, quantity: Int, bluetoothAdapter: BluetoothAdapter?, deviceAddress: String?)
    }
}