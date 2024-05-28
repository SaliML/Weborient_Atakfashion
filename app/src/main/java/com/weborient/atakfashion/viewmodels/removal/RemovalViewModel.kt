package com.weborient.atakfashion.viewmodels.removal

import android.app.Activity
import android.content.Context
import android.icu.util.Calendar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.handlers.pdf.PDFHandler
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.repositories.RemovaledItemRepository

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
        }
    }

    /**
     * Kiválasztott termékek exportálása PDF-be
     */
    fun exportRemovaledProductsToPDF(activity: Activity){
        if (selectedDate.value != null && removaledProductList.value != null){
            PDFHandler.generatePDF(activity, removaledProductList.value!!, selectedDate.value!!)
        }
    }
}