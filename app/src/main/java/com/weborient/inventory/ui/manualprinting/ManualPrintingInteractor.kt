package com.weborient.inventory.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler

class ManualPrintingInteractor(private val presenter: IManualPrintingContract.IManualPrintingPresenter): IManualPrintingContract.IManualPrintingInteractor {
    override fun generateQRCodeFromText(text: String) {
        presenter.onGeneratedQRCode(QRCodeHandler.generateQRCode(text))
    }

    override fun isPrinterPaired(pairedDevices: Set<BluetoothDevice>?): Boolean {
        if(PrinterHandler.searchPrinter(pairedDevices, AppConfig.macAddress) != null){
            return true
        }

        return false
    }

    override fun print(
        image: Bitmap,
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ) {
        val printResult = PrinterHandler.printImage(image, quantity, deviceAddress, bluetoothAdapter)
        val teszt = ""
    }


}