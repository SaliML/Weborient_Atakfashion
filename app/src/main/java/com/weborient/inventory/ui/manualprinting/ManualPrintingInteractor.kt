package com.weborient.inventory.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.models.api.getdata.ProductDataBase
import com.weborient.inventory.models.api.quantitychange.ProductQuantityChangeResponse
import com.weborient.inventory.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class ManualPrintingInteractor(private val presenter: IManualPrintingContract.IManualPrintingPresenter): IManualPrintingContract.IManualPrintingInteractor,
    IApiResponseHandler {
    override fun getProducts() {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callGetAllProducts(), ApiCallType.AllProducts, this)
        }
    }


    override fun generateQRCodeFromText(text: String) {
        presenter.onGeneratedQRCode(QRCodeHandler.generateQRCode(text))
    }

    override fun isPrinterPaired(pairedDevices: Set<BluetoothDevice>?): Boolean {
        if(PrinterHandler.searchPrinter(pairedDevices, AppConfig.macAddress) != null){
            return true
        }

        return false
    }

    override fun printBluetooth(
        image: Bitmap,
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ) {
        printBluetoothAsync(image, quantity,  bluetoothAdapter, deviceAddress)
    }

    override fun printWifi(
        image: Bitmap,
        quantity: Int,
        ipAddress: String?
    ) {
        printWifiAsync(image, quantity, ipAddress)
    }

    private fun printBluetoothAsync(
        image: Bitmap,
        quantity: Int,
        bluetoothAdapter: BluetoothAdapter?,
        deviceAddress: String?
    ) = GlobalScope.async {
        val printResult = PrinterHandler.printImageBluetooth(image, quantity, deviceAddress, bluetoothAdapter)

        withContext(Dispatchers.Main){
            presenter.onPrintResult(printResult)
        }
    }

    private fun printWifiAsync(
        image: Bitmap,
        quantity: Int,
        ipAddress: String?
    ) = GlobalScope.async {
        val printResult = PrinterHandler.printImageWifi(image, quantity, ipAddress)

        withContext(Dispatchers.Main){
            presenter.onPrintResult(printResult)
        }
    }

    override fun onSuccessful(responseType: ApiCallType, result: Any?, param: String?) {
        when(responseType){
            ApiCallType.AllProducts->{
                val response = result as ProductDataBase

                ItemRepository.products = response.datas
                presenter.onRetrievedProducts(response.datas)
            }
            else->{}
        }
    }

    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?, param: String?) {
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