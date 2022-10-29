package com.weborient.inventory.ui.`in`

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.repositories.item.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class InInteractor(private val presenter: IInContract.IInPresenter): IInContract.IInInteractor {
    override fun getItems() {
        ItemRepository.items.forEach {
            it.isSelected = false
        }

        presenter.onRetrievedItems(ItemRepository.items)
    }

    override fun setSelectedItem(item: ItemModel?) {
        //teszt, kijelölés törlése
        ItemRepository.items.forEach {
            it.isSelected = false
        }

        ItemRepository.selectedItem = item
        ItemRepository.selectedItem?.isSelected = true
        presenter.onSelectedItem()
    }

    override fun uploadSelectedItem(quantity: Int) {
        ItemRepository.selectedItem?.let{
            //Csak teszt miatt, feltöltés imitálás
            ItemRepository.lastUploadedID = it.id
            presenter.onUploadedResult(true)
        }
    }

    override fun print(
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ){
        if(ItemRepository.selectedItem != null){
            printAsync(ItemRepository.selectedItem!!.id, quantity, bluetoothAdapter, deviceAddress)
        }
        else{
            presenter.onPrintResult(PrintResult.UnknownError)
        }
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