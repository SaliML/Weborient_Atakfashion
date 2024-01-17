package com.weborient.inventory.ui.edit

import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.api.ApiServiceHandler
import com.weborient.inventory.handlers.api.ServiceBuilder
import com.weborient.inventory.models.api.getdata.GetDataByIDBase
import com.weborient.inventory.models.api.getdata.GetDataByIDBody
import com.weborient.inventory.models.api.getdata.OneProductDataBase
import com.weborient.inventory.models.api.getdata.OneProductDataBody
import com.weborient.inventory.models.api.modifydata.ModifyDataByIDBody
import com.weborient.inventory.models.api.modifydata.ModifyDataByIDResponse
import com.weborient.inventory.models.api.newproduct.NewProductGetDataResponse
import com.weborient.inventory.models.api.quantitychange.ProductQuantityChangeResponse
import com.weborient.inventory.models.api.sendproduct.ProductSendData
import com.weborient.inventory.repositories.item.ItemRepository
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

    override fun onSuccessful(responseType: ApiCallType, result: Any?, param: String?) {
        when(responseType){
            ApiCallType.GetOneProductDetails->{
                val response = result as GetDataByIDBase

                presenter.onFetchedProduct(response, ItemRepository.categories, ItemRepository.templates, ItemRepository.units, ItemRepository.packagetypes, ItemRepository.productstatuses, ItemRepository.taxes)
            }
            ApiCallType.NewProductGetData->{
                val response = result as NewProductGetDataResponse

                ItemRepository.categories = response.datas?.categories
                ItemRepository.templates = response.datas?.templates
                ItemRepository.units = response.datas?.units
                ItemRepository.packagetypes = response.datas?.packagetypes
                ItemRepository.productstatuses = response.datas?.productstatuses
                ItemRepository.taxes = response.datas?.taxes

                /*presenter.onRetrievedCategories(ItemRepository.categories)
                presenter.onRetrievedTemplates(ItemRepository.templates)
                presenter.onRetrievedUnits(ItemRepository.units)
                presenter.onRetrievedPackageTypes(ItemRepository.packagetypes)
                presenter.onRetrievedStatuses(ItemRepository.productstatuses)
                presenter.onRetrievedTaxes(ItemRepository.taxes)*/
            }
            ApiCallType.EditProduct ->{
                val response = result as ModifyDataByIDResponse

                if (!response.text.isNullOrEmpty()){
                    //Sikeres módosítás
                    presenter.onSuccessful("Sikeres módosítás!")
                }
                else{
                    presenter.onFailure("Sikertelen módosítás!")
                }
            }
            else->{}
        }
    }

    override fun onFailure(
        responseType: ApiCallType,
        result: Any?,
        throwable: Throwable?,
        param: String?
    ) {
        when(responseType){
            ApiCallType.GetOneProductDetails->{
                presenter.onFailure("Hiba történt az adatok lekérdezése során!")
            }
            ApiCallType.NewProductGetData->{
                presenter.onFailure("Hiba történt az adatok lekérdezése során")
            }
            ApiCallType.EditProduct->{
                presenter.onFailure("Hiba történt a termék módosítása során")
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