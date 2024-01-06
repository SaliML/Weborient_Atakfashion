package com.weborient.inventory.ui.newproduct

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.models.api.newproduct.ArrayElement
import com.weborient.inventory.models.api.sendproduct.ProductSendData
import com.weborient.inventory.models.interfaces.IResponseDialogHandler

/**
 * MVP minta az új termék felülethez
 */
interface INewProductContract {
    interface INewProductView{
        fun setItemID(id: String?)
        fun setCategories(categories: ArrayList<ArrayElement>?)
        fun setPackageTypes(packageTypes: ArrayList<ArrayElement>?)
        fun setUnits(units: ArrayList<ArrayElement>?)
        fun setStatuses(statuses: ArrayList<ArrayElement>?)
        fun setTemplates(templates: ArrayList<ArrayElement>?)
        fun setTaxes(taxes: ArrayList<ArrayElement>?)
        fun setGrossPrice(price: String)
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showTimedInformationDialog(information: String, type: DialogTypeEnums)
        fun showNameError(error: String?)
        fun showDescriptionError(error: String?)
        fun showQuantityError(error: String?)
        fun showCategoryError(error: String?)
        fun showPresentationError(error: String?)
        fun showUnitError(error: String?)
        fun showStatusError(error: String?)
        fun showTemplateError(error: String?)
        fun showTaxError(error: String?)
        fun showGrossPriceError(error: String?)
        fun showPrintButton()
        fun showBluetoothDialog()
        fun showNetworkDialog()
        fun showProgress(information: String)
        fun hideProgress()
        fun closeFragment()
    }

    interface INewProductPresenter: IResponseDialogHandler {
        fun getDatas()
        fun onClickedBackButton()
        fun onClickedUploadButton(name: String?, description: String?, quantity: String?, category: ArrayElement?, packageType: ArrayElement?, unit: ArrayElement?, status: ArrayElement?, template: ArrayElement?, tax: ArrayElement?, grossPrice: String?)
        fun onClickedPrintButton(id: String, quantity: String?, bluetoothAdapter: BluetoothAdapter?)
        fun onUploadedResult(isSuccessful: Boolean, id: String?)
        fun onRetrievedCategories(categories: ArrayList<ArrayElement>?)
        fun onRetrievedPackageTypes(packageTypes: ArrayList<ArrayElement>?)
        fun onRetrievedUnits(units: ArrayList<ArrayElement>?)
        fun onRetrievedStatuses(statuses: ArrayList<ArrayElement>?)
        fun onRetrievedTemplates(templates: ArrayList<ArrayElement>?)
        fun onRetrievedTaxes(taxes: ArrayList<ArrayElement>?)
        fun onReceivedNewProductID(id: String)
        fun onDialogResult(result: DialogResultEnums)
        fun onPrintResult(result: PrintResult)
    }

    interface INewProductInteractor{
        fun uploadProduct(newProduct: ProductSendData)
        fun getDatas()
        fun print(id: String, quantity: Int, bluetoothAdapter: BluetoothAdapter?, deviceAddress: String?)
    }
}