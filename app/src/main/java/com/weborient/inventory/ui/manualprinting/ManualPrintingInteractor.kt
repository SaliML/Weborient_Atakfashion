package com.weborient.inventory.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

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
        printAsync(image, quantity,  bluetoothAdapter, deviceAddress)
    }

    private fun printAsync(
        image: Bitmap,
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ) = GlobalScope.async {
        val printResult = PrinterHandler.printImage(image, quantity, deviceAddress, bluetoothAdapter)

        withContext(Dispatchers.Main){
            presenter.onPrintResult(printResult)
        }
    }
}