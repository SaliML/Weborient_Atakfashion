package com.weborient.inventory.ui.`in`

import android.bluetooth.BluetoothAdapter
import android.graphics.Bitmap
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.models.interfaces.IResponseDialogHandler

/**
 * MVP minta a főoldalra
 */
interface IInContract {
    interface IInView{
        fun showPrintButton()
        fun showBluetoothDialog()
        fun showNetworkDialog()
        fun showProgress(information: String)
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showQuantityError(error: String?)
        fun showAddNewItemFragment()
        fun showItems(productList: ArrayList<ProductData>)
        fun refreshList()
        fun hideProgress()
        fun hidePrintButton()
        fun clearQuantity()
        fun closeActivity()
    }

    interface IInPresenter: IResponseDialogHandler {
        fun getItems()

        fun onClickedUploadButton(quantity: String?)
        fun onClickedPrintButton(quantity: String?)
        fun onClickedBackButton()
        fun onClickedAddButton()
        fun onRetrievedItems(productList: ArrayList<ProductData>)
        fun onClickedProduct(product: ProductData?)
        fun onSelectedProduct()
        fun onDialogResult(result: DialogResultEnums)
        fun onPrintResult(result: PrintResult)
        fun onUploadedResult(isSuccessful: Boolean)
    }

    interface IInInteractor{
        fun getItems()
        fun setSelectedProduct(product: ProductData?)
        fun uploadSelectedProduct(quantity: Int)
        fun printWifi(quantity: Int, ipAddress: String?)
    }
}