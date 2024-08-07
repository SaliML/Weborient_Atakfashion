package com.weborient.atakfashion.ui.`in`

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiCallResponse
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.handlers.printer.PrintResult
import com.weborient.atakfashion.handlers.printer.PrinterHandler
import com.weborient.atakfashion.handlers.qrcode.QRCodeHandler
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.models.api.getdata.ProductDataBase
import com.weborient.atakfashion.models.api.quantitychange.ProductQuantityChangeRequest
import com.weborient.atakfashion.models.api.quantitychange.ProductQuantityChangeResponse
import com.weborient.atakfashion.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class InInteractor(private val presenter: IInContract.IInPresenter): IInContract.IInInteractor,
    IApiResponseHandler {
    override fun getItems() {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callGetAllProducts(), ApiCallType.AllProducts, this)
        }
    }

    override fun setSelectedProduct(product: ProductData?) {
        //teszt, kijelölés törlése
        ItemRepository.products.forEach {
            it.isSelected = false
        }

        ItemRepository.selectedProduct = product
        ItemRepository.selectedProduct?.isSelected = true
        presenter.onSelectedProduct()
    }

    override fun uploadSelectedProduct(quantity: Int) {
        ItemRepository.selectedProduct?.let{ product ->

            ServiceBuilder.createServiceWithoutBearer()

            AppConfig.apiServiceWithoutBearer?.let{ service ->
                ApiServiceHandler.apiService(service.callChangeQuantitySendData(ProductQuantityChangeRequest(product.id, quantity)), ApiCallType.AddQuantityToProduct, this)
            }
        }
    }

    override fun printWifi(
        quantity: Int,
        ipAddress: String?
    ) {
        if(ItemRepository.selectedProduct != null){
            printWifiAsync(ItemRepository.selectedProduct!!.id, quantity, ipAddress)
        }
        else{
            presenter.onPrintResult(PrintResult.MissingProductID)
        }
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
    override fun onResult(callResponse: ApiCallResponse) {
        when(callResponse.responseType){
            ApiCallType.AllProducts->{
                if(callResponse.isSuccessful){
                    val response = callResponse.result as ProductDataBase

                    ItemRepository.products = response.datas
                    presenter.onRetrievedItems(response.datas)
                }
                else{
                    presenter.onFailure("Hiba történt a termékek lekérdezése során!")
                }
            }
            ApiCallType.AddQuantityToProduct->{
                if(callResponse.isSuccessful){
                    val response = callResponse.result as ProductQuantityChangeResponse

                    //setSelectedProduct(null)
                    presenter.onUploadedResult(true)
                    presenter.onSuccessful(response.text?: "Mennyiség hozzáadva a készlethez!")
                }
                else{
                    presenter.onFailure("Hiba történt a mennyiség hozzáadása során!")
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