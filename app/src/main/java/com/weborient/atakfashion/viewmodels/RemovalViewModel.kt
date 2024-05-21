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
     * Kiválasztott dátum szöveg
     */
    var selectedDateText = MutableLiveData(SimpleDateFormat(AppConfig.DATE_FORMAT2).format(Calendar.getInstance().time))

    val removaledProductList = MutableLiveData<ArrayList<ProductData>>()

    /**
     * Kiadott termékek kinyerése
     */
    fun getRemovaledProducts(context: Context, date: Date){
        removaledProductList.value = RemovaledItemRepository.ReadRemovaledProducts(context, date, false)?.products
    }

    /**
     * Dátumok beállítása
     */
    fun setSelectedDate(time: Date){
        selectedDate.value = time
        selectedDateText.value = SimpleDateFormat(AppConfig.DATE_FORMAT2).format(time)
    }
}