package com.weborient.atakfashion.ui.edit

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiCallResponse
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.models.api.getdata.GetDataByIDBase
import com.weborient.atakfashion.models.api.getdata.GetDataByIDBody
import com.weborient.atakfashion.models.api.modifydata.ModifyDataByIDBody
import com.weborient.atakfashion.models.api.modifydata.ModifyDataByIDResponse
import com.weborient.atakfashion.models.api.newproduct.NewProductGetDataResponse
import com.weborient.atakfashion.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler

class EditInteractor(private val presenter: IEditContract.IEditPresenter): IEditContract.IEditInteractor,
    IApiResponseHandler {
    override fun getItemByID(id: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callGetDataByID(GetDataByIDBody(id)), ApiCallType.GetOneProductDetails, this)
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

    override fun uploadProduct(product: ModifyDataByIDBody) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callModifyDataByID(product), ApiCallType.EditProduct, this)
        }
    }

    override fun onResult(callResponse: ApiCallResponse) {
        when(callResponse.responseType){
            ApiCallType.GetOneProductDetails->{
                if (callResponse.isSuccessful){
                    val response = callResponse.result as GetDataByIDBase

                    presenter.onFetchedProduct(response, ItemRepository.categories, ItemRepository.templates, ItemRepository.units, ItemRepository.packagetypes, ItemRepository.productstatuses, ItemRepository.taxes)
                }
                else{
                    presenter.onFailure("Hiba történt az adatok lekérdezése során!")
                }
            }
            ApiCallType.NewProductGetData->{
                if (callResponse.isSuccessful){
                    val response = callResponse.result as NewProductGetDataResponse

                    ItemRepository.categories = response.datas?.categories
                    ItemRepository.templates = response.datas?.templates
                    ItemRepository.units = response.datas?.units
                    ItemRepository.packagetypes = response.datas?.packagetypes
                    ItemRepository.productstatuses = response.datas?.productstatuses
                    ItemRepository.taxes = response.datas?.taxes
                }
                else{
                    presenter.onFailure("Hiba történt az adatok lekérdezése során")
                }

            }
            ApiCallType.EditProduct ->{
                if (callResponse.isSuccessful)
                {
                    val response = callResponse.result as ModifyDataByIDResponse

                    if (!response.text.isNullOrEmpty()){
                        //Sikeres módosítás
                        presenter.onSuccessful("Sikeres módosítás!")
                    }
                    else{
                        presenter.onFailure("Sikertelen módosítás!")
                    }
                }
                else{
                    presenter.onFailure("Hiba történt a termék módosítása során")
                }
            }
            ApiCallType.Connection->{
                if (!callResponse.isSuccessful){
                    presenter.onFailure("Hiba történt a kapcsolódás során!")
                }
            }
            ApiCallType.Timeout->{
                if (!callResponse.isSuccessful){
                    presenter.onFailure("Időtúllépés hiba!")
                }
            }
            ApiCallType.Unknown->{
                if (!callResponse.isSuccessful){
                    presenter.onFailure("Ismeretlen hiba történt!")
                }
            }
            else->{}
        }
    }
}