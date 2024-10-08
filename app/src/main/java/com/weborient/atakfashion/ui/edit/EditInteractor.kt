package com.weborient.atakfashion.ui.edit

import com.google.gson.Gson
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
import com.weborient.atakfashion.models.api.template.TemplateSendData
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
            tempData.selecteddata?.add(element.id)
        }
        else{
            selectedTemplateDatas.add(TemplateData(templateDataID, "", arrayListOf(element), arrayListOf(element.id)))
        }
    }

    /**
     * Sablon érték törlése a kiválasztott értékek közül
     */
    override fun removeTemplateData(templateDataID: String, element: TemplateDataArrayElement) {
        val tempData = selectedTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        tempData?.selecteddata?.remove(element.id)

        if (tempData?.selecteddata?.isEmpty() == true){
            selectedTemplateDatas.removeIf { it.id.equals(templateDataID) }
        }
    }

    /**
     * Érték ellenőrzése a termék sablon értékei között
     */
    override fun checkTemplateData(templateDataID: String, element: TemplateDataArrayElement): Boolean{
        val fetchedTemplateData = fetchedProductTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        if (fetchedTemplateData?.selecteddata?.contains(element.id) == true){
            val selectedTemplateData = selectedTemplateDatas.firstOrNull { selectedData ->
                selectedData.id.equals(templateDataID)
            }

            if (selectedTemplateData != null){
                selectedTemplateData.selecteddata!!.add(element.id)
            }
            else{
                selectedTemplateDatas.add(TemplateData(fetchedTemplateData.id, fetchedTemplateData.name, fetchedTemplateData.data, arrayListOf(element.id)))
            }

            return true
        }

        return false
    }

    override fun uploadProduct(product: ModifyDataByIDBody) {
        ServiceBuilder.createServiceWithoutBearer()

        val properties = arrayListOf<TemplateSendData>()
        selectedTemplateDatas.forEach { templateData ->
            if (templateData.selecteddata?.isNotEmpty() == true){
                val propertyValues = arrayListOf<String>()

                templateData.selecteddata?.forEach {
                    propertyValues.add(it.toString())
                }

                properties.add(TemplateSendData(templateData.id, propertyValues))
            }
        }

        product.properties = properties
        //val teszt = Gson().toJson(product)
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
                    //Lekérdezett termékhez tartozó sablon adatokat rögzíteni kell az fetchedProductTemplateDatas tömbbe!

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
                    selectedTemplateDatas.clear()
                    (callResponse.result as TemplateDataBase).datas.templatedatas.forEach {
                        if (!it.data.isNullOrEmpty()){
                            addTemplateData(it.id, it.data.first())
                        }
                    }
                    //presenter.onFetchedTemplateDatas((callResponse.result as TemplateDataBase).datas.templatedatas)
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