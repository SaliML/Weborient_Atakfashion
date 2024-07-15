package com.weborient.atakfashion.ui.manualprinting

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiCallResponse
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.handlers.printer.PrinterHandler
import com.weborient.atakfashion.handlers.qrcode.QRCodeHandler
import com.weborient.atakfashion.models.api.getdata.ProductDataBase
import com.weborient.atakfashion.repositories.item.ItemRepository
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

    override fun onResult(callResponse: ApiCallResponse) {
        when(callResponse.responseType){
            ApiCallType.AllProducts->{
                if(callResponse.isSuccessful){
                    val response = callResponse.result as ProductDataBase

                    ItemRepository.products = response.datas
                    presenter.onRetrievedProducts(response.datas)
                }
                else{
                    presenter.onFailure("Hiba történt a termékek lekérdezése során!")
                }
            }
            ApiCallType.Connection->{
                if(!callResponse.isSuccessful){
                    presenter.onFailure("Hiba történt a kapcsolódás során!")
                }
            }
            ApiCallType.Timeout->{
                if(!callResponse.isSuccessful){
                    presenter.onFailure("Időtúllépés hiba!")
                }
            }
            ApiCallType.Unknown->{
                if(!callResponse.isSuccessful){
                    presenter.onFailure("Ismeretlen hiba történt!")
                }
            }
            else->{

            }
        }
    }
}