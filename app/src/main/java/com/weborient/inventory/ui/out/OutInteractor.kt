package com.weborient.inventory.ui.out

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import com.weborient.inventory.repositories.item.ItemRepository

class OutInteractor(private val presenter: IOutContract.IOutPresenter): IOutContract.IOutInteractor {
    override fun decreaseAmount(amount: Int) {
        presenter.onResultDecreaseAmount(true)
    }

    override fun getItemByID(itemID: String) {
        presenter.onFetchedItem(ItemRepository.testItem)
    }
}