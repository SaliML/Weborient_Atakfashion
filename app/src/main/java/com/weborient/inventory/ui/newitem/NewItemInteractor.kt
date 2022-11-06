package com.weborient.inventory.ui.newitem

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.repositories.inventory.InventoryRepository
import kotlinx.coroutines.*

class NewItemInteractor(private val presenter: INewItemContract.INewItemPresenter): INewItemContract.INewItemInteractor {
    override fun uploadItem(item: ItemModel, quantity: Int) {
        //Ideiglenesen
        presenter.onUploadedResult(true, "123456")
    }

    override fun getCategories() {
        presenter.onRetrievedCategories(InventoryRepository.categories)
    }

    override fun getPresentation() {
        presenter.onRetrievedPresentations(InventoryRepository.presentations)
    }

    override fun getUnits() {
        presenter.onRetrievedUnits(InventoryRepository.units)
    }

    override fun getStatuses() {
        presenter.onRetrievedStatuses(InventoryRepository.statuses)
    }

    override fun getTemplates() {
        presenter.onRetrievedTemplates(InventoryRepository.templates)
    }

    override fun print(
        id: String,
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ){
        printAsync(id, quantity, bluetoothAdapter, deviceAddress)
    }

    private fun printAsync(
        id: String,
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ) = GlobalScope.async {

        val printResult = PrinterHandler.printImage(QRCodeHandler.generateQRCode(id), quantity, deviceAddress, bluetoothAdapter)

        withContext(Dispatchers.Main){
            presenter.onPrintResult(printResult)
        }
    }
}