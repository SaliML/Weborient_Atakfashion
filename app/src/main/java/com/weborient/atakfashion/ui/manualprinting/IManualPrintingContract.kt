package com.weborient.atakfashion.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.printer.PrintResult
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.models.interfaces.IResponseDialogHandler

/**
 * MVP minta a manuális nyomtatás oldalra
 */
interface IManualPrintingContract {

    /**
     * View interfésze
     */
    interface IManualPrintingView{
        fun clearQRCode()
        fun clearText()
        fun clearAmount()
        fun setQRCode(bitmap: Bitmap)
        fun showTextError(error: String?)
        fun showQuantityError(error: String?)
        fun showBluetoothDialog()
        fun showWifiDialog()
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showProgress(information: String)
        fun showItems(productList: ArrayList<ProductData>)
        fun navigateToScannerActivity()
        fun hideProgress()
        fun closeActivity()
    }

    /**
     * Presenter interfésze
     */
    interface IManualPrintingPresenter: IResponseDialogHandler {
        fun onClickedBackButton()
        fun onClickedPrintButton(qrCode: Bitmap?, quantity: Int?)
        fun onClickedScanButton()
        fun onGeneratedQRCode(bitmap: Bitmap)
        fun onDialogResult(result: DialogResultEnums)
        fun onPrintResult(result: PrintResult)
        fun onClickedProduct(product: ProductData?)

        fun onRetrievedProducts(productList: ArrayList<ProductData>)
        fun generateQRCode(text: String?)
        fun getProducts()
    }

    /**
     * Interactor interfésze
     */
    interface IManualPrintingInteractor{
        fun getProducts()
        fun generateQRCodeFromText(text: String)
        fun isPrinterPaired(pairedDevices: Set<BluetoothDevice>?): Boolean
        fun printBluetooth(image: Bitmap, quantity: Int, bluetoothAdapter: BluetoothAdapter?, deviceAddress: String?)
        fun printWifi(image: Bitmap, quantity: Int, ipAddress: String?)
    }
}