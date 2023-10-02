package com.weborient.inventory.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.printer.PrintResult

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
        fun showQRCode(bitmap: Bitmap)
        fun showTextError(error: String?)
        fun showQuantityError(error: String?)
        fun showBluetoothDialog()
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showProgress(information: String)
        fun hideProgress()
        fun closeActivity()
    }

    /**
     * Presenter interfésze
     */
    interface IManualPrintingPresenter{
        fun onClickedBackButton()
        fun onClickedGenerateButton(text: String?)
        fun onClickedPrintButton(qrCode: Bitmap?, quantity: Int?, bluetoothAdapter: BluetoothAdapter?)
        fun onGeneratedQRCode(bitmap: Bitmap)
        fun onDialogResult(result: DialogResultEnums)
        fun onPrintResult(result: PrintResult)
    }

    /**
     * Interactor interfésze
     */
    interface IManualPrintingInteractor{
        fun generateQRCodeFromText(text: String)
        fun isPrinterPaired(pairedDevices: Set<BluetoothDevice>?): Boolean
        fun print(image: Bitmap, quantity: Int, bluetoothAdapter: BluetoothAdapter?, deviceAddress: String?)
    }
}