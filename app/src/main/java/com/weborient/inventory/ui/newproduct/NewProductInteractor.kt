package com.weborient.inventory.ui.newproduct

import android.bluetooth.BluetoothAdapter
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.models.api.newproduct.NewProductGetDataResponse
import com.weborient.inventory.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import kotlinx.coroutines.*

class NewProductInteractor(private val presenter: INewProductContract.INewProductPresenter): INewProductContract.INewProductInteractor, IApiResponseHandler {
    override fun uploadProduct(item: ItemModel, quantity: Int) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            //ApiServiceHandler.apiService(service.callNewProductSendData())
        }
    }

    /**
     * Új termék felvétel felület betöltésekor lekérdezzük az aktuális / szükséges adatokat, csoportokat
     */
    override fun getDatas() {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callNewProductGetData(), ApiCallType.NewProductGetData, this)
        }
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

    /**
     * Sikeres művelet végrehajtás
     */
    override fun onSuccessful(responseType: ApiCallType, result: Any?) {
        when(responseType){
            ApiCallType.NewProductGetData->{
                val response = result as NewProductGetDataResponse

                ItemRepository.categories = response.datas?.categories
                ItemRepository.templates = response.datas?.templates
                ItemRepository.units = response.datas?.units
                ItemRepository.packagetypes = response.datas?.packagetypes
                ItemRepository.productstatuses = response.datas?.productstatuses

                presenter.onRetrievedCategories(ItemRepository.categories)
                presenter.onRetrievedTemplates(ItemRepository.templates)
                presenter.onRetrievedUnits(ItemRepository.units)
                presenter.onRetrievedPackageTypes(ItemRepository.packagetypes)
                presenter.onRetrievedStatuses(ItemRepository.productstatuses)
            }
            ApiCallType.NewProductSendData->{
                val response = result as String?
            }
            else->{}
        }
    }

    /**
     * Hiba történt a kérés végrehajtása során
     */
    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?) {
        when(responseType){
            ApiCallType.NewProductGetData->{
                presenter.onFailure("Hiba történt az adatok lekérdezése során!")
            }
            ApiCallType.NewProductSendData->{
                presenter.onFailure("Hiba történt a termék felvétele során!")
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