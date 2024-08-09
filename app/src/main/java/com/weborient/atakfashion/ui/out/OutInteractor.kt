package com.weborient.atakfashion.ui.out

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiCallResponse
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.models.api.getdata.OneProductDataBase
import com.weborient.atakfashion.models.api.getdata.OneProductDataBody
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.models.api.quantitychange.ProductQuantityChangeRequest
import com.weborient.atakfashion.models.api.quantitychange.ProductQuantityChangeResponse
import com.weborient.atakfashion.repositories.RemovaledItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler

class OutInteractor(private val presenter: IOutContract.IOutPresenter): IOutContract.IOutInteractor,
    IApiResponseHandler {
    override fun decreaseQuantity(quantity: Int, productID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            RemovaledItemRepository.productToRemoval?.quantity = quantity
            ApiServiceHandler.apiService(service.callChangeQuantitySendData(ProductQuantityChangeRequest(productID, quantity.unaryMinus())), ApiCallType.SubtractionQuantityFromProduct, this)
        }
    }

    override fun getItemByID(itemID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callOneProductGetData(OneProductDataBody(itemID)), ApiCallType.GetOneProduct, this)
        }
    }

    /**
     * Termék ideiglenes rögzítése
     */
    override fun setTempProduct(product: ProductData) {
        RemovaledItemRepository.productToRemoval = product
    }

    override fun onResult(callResponse: ApiCallResponse) {
        try{
            when(callResponse.responseType){
                ApiCallType.GetOneProduct->{
                    if(callResponse.isSuccessful){
                        val response = callResponse.result as OneProductDataBase

                        presenter.onFetchedItem(response.datas)
                    }
                    else{
                        presenter.onFailure("Hiba történt a termékek lekérdezése során!")
                    }
                }
                ApiCallType.SubtractionQuantityFromProduct->{
                    if(callResponse.isSuccessful){
                        val response = callResponse.result as ProductQuantityChangeResponse

                        if (response.text?.contains("hiba", true) == false){
                            presenter.addRemovableProduct()
                        }

                        presenter.onResultDecreaseAmount()
                        presenter.onSuccessful(response.text?: "Sikeres anyagelvonás!")
                    }
                    else{
                        var response: ProductQuantityChangeResponse? = null

                        if (callResponse.result != null){
                            response = callResponse.result as ProductQuantityChangeResponse
                        }

                        presenter.onFailure(response?.text?: "Hiba történt a mennyiség csökkentése során!")
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
        catch (e: Exception){
            presenter.onFailure("Hiba történt a mennyiség elvonása során!\n ${e.message}")
        }

    }
}