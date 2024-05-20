package com.weborient.atakfashion.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weborient.atakfashion.models.removal.RemovaledGroup
import com.weborient.atakfashion.repositories.RemovaledItemRepository
import java.util.Date

class RemovalViewModel: ViewModel() {
    var test = MutableLiveData<String>()

    private val _removaledProductGroup = MutableLiveData<RemovaledGroup>()
    val removaledProductGroup: LiveData<RemovaledGroup>
        get() = _removaledProductGroup

    /**
     * Kiadott termékek kinyerése
     */
    fun getRemovaledProducts(context: Context, date: Date){
        _removaledProductGroup.value = RemovaledItemRepository.ReadRemovaledProducts(context, date, false)
    }
}