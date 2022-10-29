package com.weborient.inventory.ui.`in`

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.models.ItemModel

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
        fun showItems(itemList: ArrayList<ItemModel>)
        fun refreshList()
        fun hideProgress()
        fun hidePrintButton()
        fun clearQuantity()
        fun closeActivity()
    }

    interface IInPresenter{
        fun getItems()

        fun onClickedUploadButton(quantity: String?)
        fun onClickedPrintButton(quantity: String?, bluetoothAdapter: BluetoothAdapter?)
        fun onClickedBackButton()
        fun onClickedAddButton()
        fun onRetrievedItems(itemList: ArrayList<ItemModel>)
        fun onClickedItem(item: ItemModel?)
        fun onSelectedItem()
        fun onDialogResult(result: DialogResultEnums)
        fun onPrintResult(result: PrintResult)
        fun onUploadedResult(isSuccessful: Boolean)
    }

    interface IInInteractor{
        fun getItems()
        fun setSelectedItem(item: ItemModel?)
        fun uploadSelectedItem(quantity: Int)
        fun print(quantity: Int, bluetoothAdapter: BluetoothAdapter?, deviceAddress: String?)
    }
}