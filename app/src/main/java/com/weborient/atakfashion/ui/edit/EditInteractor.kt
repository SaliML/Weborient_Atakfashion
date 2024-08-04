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
import com.weborient.atakfashion.models.api.template.TemplateData
import com.weborient.atakfashion.models.api.template.TemplateDataArrayElement
import com.weborient.atakfashion.models.api.template.TemplateDataBase
import com.weborient.atakfashion.models.api.template.TemplateFilter
import com.weborient.atakfashion.repositories.item.ItemRepository
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler

class EditInteractor(private val presenter: IEditContract.IEditPresenter): IEditContract.IEditInteractor, IApiResponseHandler {
    /**
     * Kiválasztott sablon adatok
     */
    private var selectedTemplateDatas = arrayListOf<TemplateData>()

    /**
     * Lekérdezett termék sablon adatai
     */
    private var fetchedProductTemplateDatas = arrayListOf<TemplateData>()

    /**
     * Kiválasztott termék adatai
     */
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

    /**
     * Sablon érték hozzáadása a kiválasztott értékekhez
     */
    override fun addTemplateData(templateDataID: String, element: TemplateDataArrayElement) {
        val tempData = selectedTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        if(tempData != null){
            tempData.data?.add(element)
        }
        else{
            selectedTemplateDatas.add(TemplateData(templateDataID, "", arrayListOf(element), arrayListOf()))
        }
    }

    /**
     * Sablon érték törlése a kiválasztott értékek közül
     */
    override fun removeTemplateData(templateDataID: String, element: TemplateDataArrayElement) {
        val tempData = selectedTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        tempData?.data?.remove(element)
    }

    /**
     * Érték ellenőrzése a termék sablon értékei között
     */
    override fun checkTemplateData(templateDataID: String, element: TemplateDataArrayElement): Boolean{
        val fetchedTemplateData = fetchedProductTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        fetchedTemplateData?.let { fetchedData ->
            val fetchedTemplateDataValue = fetchedData.data?.firstOrNull { value ->
                value.id == element.id
            }

            if (fetchedTemplateDataValue != null){
                val selectedTemplateData = selectedTemplateDatas.firstOrNull { selectedData ->
                    selectedData.id.equals(fetchedData.id)
                }

                if (selectedTemplateData != null){
                    selectedTemplateData.selecteddata!!.add(fetchedTemplateDataValue.id)
                }
                else{
                    selectedTemplateDatas.add(TemplateData(fetchedData.id, fetchedData.name, fetchedData.data, arrayListOf(fetchedTemplateDataValue.id)))
                }

                return true
            }
        }

        return false
    }

    override fun uploadProduct(product: ModifyDataByIDBody) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callModifyDataByID(product), ApiCallType.EditProduct, this)
        }
    }

    /**
     * Sablon adatainak lekérdezése
     */
    override fun getTemplateDatas(templateID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callGetTemplateDatas(TemplateFilter(templateID)), ApiCallType.GetTemplateData, this)
        }
    }

    override fun onResult(callResponse: ApiCallResponse) {
        when(callResponse.responseType){
            ApiCallType.GetOneProductDetails->{
                if (callResponse.isSuccessful){
                    val response = callResponse.result as GetDataByIDBase
                    //Lekérdezett termékhez tartozó sablon adatokat rögzíteni kell az onFetchedProductTemplateDatas tömbbe!

                    if (response.datas.size > 0){
                        fetchedProductTemplateDatas = response.datas[0].templatedatas
                    }

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
            ApiCallType.GetTemplateData ->{
                if(callResponse.isSuccessful){
                    presenter.onFetchedTemplateDatas((callResponse.result as TemplateDataBase).datas.templatedatas)
                    selectedTemplateDatas.clear()
                }
                else{
                    presenter.onFailure("Hiba történt a sablon adatainak lekérdezése során")
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