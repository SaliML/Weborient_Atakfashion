package com.weborient.inventory.ui.`in`

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.handlers.printer.PrintResult
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.models.api.newproduct.NewProductGetDataResponse
import com.weborient.inventory.models.api.sendproduct.NewProductSendDataResponse
import com.weborient.inventory.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class InInteractor(private val presenter: IInContract.IInPresenter): IInContract.IInInteractor,
    IApiResponseHandler {
    override fun getItems() {
        /*ItemRepository.items.forEach {
            it.isSelected = false
        }

        presenter.onRetrievedItems(ItemRepository.items)*/

        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callGetAllProducts(), ApiCallType.NewProductGetData, this)
        }
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

    override fun onSuccessful(responseType: ApiCallType, result: Any?) {
        when(responseType){
            ApiCallType.AllProducts->{
                val response = result as NewProductGetDataResponse


            }
            else->{}
        }
    }

    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?) {
        when(responseType){
            ApiCallType.AllProducts->{
                presenter.onFailure("Hiba történt a termékek lekérdezése során!")
            }
            ApiCallType.Connection->{
                presenter.onFailure("Hiba történt a kapcsolódás során!")
            }
            ApiCallType.Timeout->{
                presenter.onFailure("Időtúllépés hiba!")
            }
            ApiCallType.Unknown->{
                presenter.onFailure("Ismeretlen hiba történt!")
            }
            else->{

            }
        }
    }
}