package com.weborient.atakfashion.viewmodels

import android.content.Context
import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.repositories.RemovaledItemRepository
import java.text.SimpleDateFormat
import java.util.Date

class RemovalViewModel: ViewModel() {
    /**
     * Kiválasztott dátum
     */
    var selectedDate = MutableLiveData(Calendar.getInstance().time)

    /**
     * Megjelenítendő terméklista
     */
    val removaledProductList = MutableLiveData<ArrayList<ProductData>>()

    /**
     * Kiadott termékek kinyerése
     */
    fun getRemovaledProducts(context: Context){
        selectedDate.value?.let{
            removaledProductList.value = RemovaledItemRepository.ReadRemovaledProducts(context, it, false)?.products ?: arrayListOf()

            var teszt = ""
        }
    }

    /**
     * Kiválasztott termékek exportálása PDF-be
     */
    fun exportRemovaledProductsToPDF(){

    }
}