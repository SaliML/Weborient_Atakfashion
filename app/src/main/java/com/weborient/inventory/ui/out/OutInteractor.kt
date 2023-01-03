package com.weborient.inventory.ui.out

import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.models.api.getdata.OneProductDataBase
import com.weborient.inventory.models.api.getdata.OneProductDataBody
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler

class OutInteractor(private val presenter: IOutContract.IOutPresenter): IOutContract.IOutInteractor,
    IApiResponseHandler {
    override fun decreaseAmount(amount: Int) {
        presenter.onResultDecreaseAmount(true)
    }

    override fun getItemByID(itemID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callOneProductGetData(OneProductDataBody(itemID)), ApiCallType.GetOneProduct, this)
        }
    }

    override fun onSuccessful(responseType: ApiCallType, result: Any?) {
        when(responseType){
            ApiCallType.GetOneProduct->{
                val response = result as OneProductDataBase

                presenter.onFetchedItem(response.datas)
            }
            else->{}
        }
    }

    override fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?) {
        when(responseType){
            ApiCallType.GetOneProduct->{
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