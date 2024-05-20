package com.weborient.atakfashion.ui.newproduct

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.handlers.printer.PrinterHandler
import com.weborient.atakfashion.handlers.qrcode.QRCodeHandler
import com.weborient.atakfashion.models.api.newproduct.NewProductGetDataResponse
import com.weborient.atakfashion.models.api.sendproduct.ProductSendData
import com.weborient.atakfashion.models.api.sendproduct.NewProductSendDataResponse
import com.weborient.atakfashion.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import kotlinx.coroutines.*

class NewProductInteractor(private val presenter: INewProductContract.INewProductPresenter): INewProductContract.INewProductInteractor, IApiResponseHandler {
    override fun uploadProduct(newProduct: ProductSendData) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callNewProductSendData(newProduct), ApiCallType.NewProductSendData, this)
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

    override fun printWifi(
        id: String,
        quantity: Int,
        ipAddress: String?
    ) {
        printWifiAsync(id, quantity, ipAddress)
    }

    private fun printWifiAsync(
        id: String,
        quantity: Int,
        ipAddress: String?
    ) = GlobalScope.async {

        val printResult = PrinterHandler.printImageWifi(QRCodeHandler.generateQRCode(id), quantity, ipAddress)

        withContext(Dispatchers.Main){
            presenter.onPrintResult(printResult)
        }
    }

    /**
     * Sikeres művelet végrehajtás
     */
    override fun onSuccessful(responseType: ApiCallType, result: Any?, param: String?) {
        when(responseType){
            ApiCallType.NewProductGetData->{
                val response = result as NewProductGetDataResponse

                ItemRepository.categories = response.datas?.categories
                ItemRepository.templates = response.datas?.templates
                ItemRepository.units = response.datas?.units
                ItemRepository.packagetypes = response.datas?.packagetypes
                ItemRepository.productstatuses = response.datas?.productstatuses
                ItemRepository.taxes = response.datas?.taxes

                presenter.onRetrievedCategories(ItemRepository.categories)
                presenter.onRetrievedTemplates(ItemRepository.templates)
                presenter.onRetrievedUnits(ItemRepository.units)
                presenter.onRetrievedPackageTypes(ItemRepository.packagetypes)
                presenter.onRetrievedStatuses(ItemRepository.productstatuses)
                presenter.onRetrievedTaxes(ItemRepository.taxes)
            }
            ApiCallType.NewProductSendData->{
                val response = result as NewProductSendDataResponse

                response.id?.let{
                    presenter.onReceivedNewProductID(it)
                }
            }
            else->{}
        }
    }

    /**
     * Hiba történt a kérés végrehajtása során
     */
    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?, param: String?) {
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