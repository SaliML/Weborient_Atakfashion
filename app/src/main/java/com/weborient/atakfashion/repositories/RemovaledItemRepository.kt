package com.weborient.atakfashion.repositories

import android.content.Context
import com.google.gson.Gson
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.file.FileHandler
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.models.removal.RemovaledGroup
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

/**
 * Kiadott termékek kezeléséért felelős osztály
 * Működési logika:
 * - Minden termék hozzáadásnál mentünk a háttértárra
 * - Mentés előtt ellenőrizzük, hogy az aktuális csoportunk dátuma egyezik-e az aktuális dátummal,
 * ha nem, akkor új listát csinálunk, hozzáadjuk és mentünk,
 * ha igen, akkor a meglévő listához adjuk hozzá és mentünk
 */
object RemovaledItemRepository {
    /**
     * Kiadott termékek csoportja
     */
    var removaledProducts: RemovaledGroup? = null

    /**
     * Elvonni kívánt termék
     */
    var productToRemoval: ProductData? = null

    /**
     * Termék hozzáadása a listához
     */
    fun AddRemovaledProduct(context: Context){
        productToRemoval?.let{ product ->
            removaledProducts?.let{ group ->

                val actualDate = Calendar.getInstance().time

                if (SimpleDateFormat(AppConfig.DATE_FORMAT).format(actualDate).equals(SimpleDateFormat(AppConfig.DATE_FORMAT).format(group.date))){
                    group.products.add(product)

                    SaveRemovaledProducts(context)

                    productToRemoval = null
                }
                else{
                    removaledProducts = RemovaledGroup(Calendar.getInstance().time, arrayListOf(product))

                    SaveRemovaledProducts(context)

                    productToRemoval = null
                }
            }?:run{
                removaledProducts = RemovaledGroup(Calendar.getInstance().time, arrayListOf(product))

                SaveRemovaledProducts(context)

                productToRemoval = null
            }
        }
    }
    /**
     * Kiadott termékek mentése a háttértárra
     */
    private fun SaveRemovaledProducts(context: Context){
        removaledProducts?.let{
            FileHandler.saveInStorage(context, AppConfig.ATAKFASHION_EXTERNAL_REMOVALED_ITEMS_DIRECTORY, "${SimpleDateFormat(AppConfig.DATE_FORMAT).format(Calendar.getInstance().time)}.json", Gson().toJson(it))
        }
    }

    /**
     * Mentett kiadott termékek kiolvasása a háttértárról dátum alapján
     */
    fun ReadRemovaledProducts(context: Context, date: Date, isNeedToStore: Boolean): RemovaledGroup?{
        val json: String? = FileHandler.readFromStorage<String>(context, AppConfig.ATAKFASHION_EXTERNAL_REMOVALED_ITEMS_DIRECTORY, "${SimpleDateFormat(AppConfig.DATE_FORMAT).format(date)}.json")

        json?.let{
            val tempRemovaledGroup = Gson().fromJson(it, RemovaledGroup::class.java)

            if (isNeedToStore){
                removaledProducts = tempRemovaledGroup
            }

            return tempRemovaledGroup
        }

        return null
    }
}