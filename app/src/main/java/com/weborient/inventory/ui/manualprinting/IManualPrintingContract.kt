package com.weborient.inventory.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums

/**
 * MVP minta a manuális nyomtatás oldalra
 */
interface IManualPrintingContract {

    /**
     * View interfésze
     */
    interface IManualPrintingView{
        fun showQRCode(bitmap: Bitmap)
        fun showTextError(error: String?)
        fun showQuantityError(error: String?)
        fun showBluetoothDialog()
        fun showInformationDialog(information: String, type: DialogTypeEnums)
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