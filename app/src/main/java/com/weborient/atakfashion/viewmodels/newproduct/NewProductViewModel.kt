package com.weborient.atakfashion.viewmodels.newproduct

import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.api.ApiCallResponse
import com.weborient.atakfashion.handlers.api.ApiServiceHandler
import com.weborient.atakfashion.handlers.api.ServiceBuilder
import com.weborient.atakfashion.models.api.sendproduct.NewProductSendDataResponse
import com.weborient.atakfashion.models.api.sendproduct.ProductSendData
import com.weborient.atakfashion.models.api.template.TemplateData
import com.weborient.atakfashion.models.api.template.TemplateDataArrayElement
import com.weborient.atakfashion.models.api.template.TemplateDataBase
import com.weborient.atakfashion.models.api.template.TemplateDatas
import com.weborient.atakfashion.models.api.template.TemplateFilter
import com.weborient.atakfashion.models.api.template.TemplateSendData
import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler

class NewProductViewModel: ViewModel(), IApiResponseHandler {

    private var selectedTemplateDatas = arrayListOf<TemplateData>()

    var productID = MutableLiveData("-")

    /**
     * API hívás válasz
     */
    var response = MutableLiveData<ApiCallResponse>()

    /**
     * Sablon adatok
     */
    var templateDatas = MutableLiveData<ArrayList<TemplateData>?>()

    /**
     * Sablon adatainak lekérdezése
     */
    fun getTemplateDatas(templateID: String) {
        ServiceBuilder.createServiceWithoutBearer()

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callGetTemplateDatas(TemplateFilter(templateID)), ApiCallType.GetTemplateData, this)
        }
    }

    /**
     * Termék feltöltése
     */
    fun uploadProduct(newProduct: ProductSendData) {
        ServiceBuilder.createServiceWithoutBearer()

        val templateSendData = arrayListOf<TemplateSendData>()

        selectedTemplateDatas.forEach {data ->
            val ids = arrayListOf<String>()

            data.data?.forEach {
                ids.add(it.id.toString())
            }

            templateSendData.add(TemplateSendData(data.id, ids))
        }

        newProduct.properties = templateSendData
        //val teszt = Gson().toJson(newProduct)

        AppConfig.apiServiceWithoutBearer?.let{ service ->
            ApiServiceHandler.apiService(service.callNewProductSendData(newProduct), ApiCallType.NewProductSendData, this)
        }
    }

    /**
     * API hívás eredményének kezelése
     */
    override fun onResult(callResponse: ApiCallResponse) {
        response.value = callResponse

        when(callResponse.responseType){
            ApiCallType.GetTemplateData->{
                if (callResponse.isSuccessful){
                    templateDatas.value = (callResponse.result as TemplateDataBase).datas.templatedatas
                    selectedTemplateDatas.clear()
                }
                else{
                    templateDatas.value = null
                }
            }
            ApiCallType.NewProductSendData->{
                if(callResponse.isSuccessful){
                    val response = callResponse.result as NewProductSendDataResponse

                    response.id?.let{
                        productID.value = it
                    }
                }
            }
            else->{}
        }
    }

    /**
     * Értékkészlet adat hozzáadás
     */
    fun addTemplateData(templateDataID: String, element: TemplateDataArrayElement){
        val tempData = selectedTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        if(tempData != null){
            tempData.data?.add(element)
        }
        else{
            selectedTemplateDatas.add(TemplateData(templateDataID, "", arrayListOf(element), arrayListOf()))
        }
    }

    /**
     * Értékkészlet adat eltávolítása
     */
    fun removeTemplateData(templateDataID: String, element: TemplateDataArrayElement){
        val tempData = selectedTemplateDatas.firstOrNull { it.id.equals(templateDataID) }

        tempData?.data?.remove(element)
    }
}