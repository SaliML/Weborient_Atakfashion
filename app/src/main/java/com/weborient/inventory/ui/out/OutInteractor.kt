package com.weborient.inventory.ui.out

import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.models.api.getdata.OneProductDataBase
import com.weborient.inventory.models.api.getdata.OneProductDataBody
import com.weborient.inventory.models.api.quantitychange.ProductQuantityChangeRequest
import com.weborient.inventory.models.api.quantitychange.ProductQuantityChangeResponse
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler

class OutInteractor(private val presenter: IOutContract.IOutPresenter): IOutContract.IOutInteractor,
    IApiResponseHandler {
    override fun decreaseQuantity(quantity: Int, productID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callChangeQuantitySendData(ProductQuantityChangeRequest(productID, quantity.unaryMinus())), ApiCallType.SubtractionQuantityFromProduct, this)
        }
    }

    override fun getItemByID(itemID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callOneProductGetData(OneProductDataBody(itemID)), ApiCallType.GetOneProduct, this)
        }
    }

    override fun onSuccessful(responseType: ApiCallType, result: Any?, param: String?) {
        when(responseType){
            ApiCallType.GetOneProduct->{
                val response = result as OneProductDataBase

                presenter.onFetchedItem(response.datas)
            }
            ApiCallType.SubtractionQuantityFromProduct->{
                val response = result as ProductQuantityChangeResponse

                presenter.onResultDecreaseAmount()
                presenter.onSuccessful(response.text?: "Sikeres anyagelvonás!")
            }
            else->{}
        }
    }

    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?, param: String?) {
        when(responseType){
            ApiCallType.GetOneProduct->{
                presenter.onFailure("Hiba történt a termékek lekérdezése során!")
            }
            ApiCallType.SubtractionQuantityFromProduct->{
                val response = result as ProductQuantityChangeResponse

                presenter.onFailure(response.text?: "Hiba történt a mennyiség csökkentése során!")
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