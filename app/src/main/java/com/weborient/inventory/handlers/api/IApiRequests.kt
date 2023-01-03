package com.weborient.inventory.handlers.api

import com.weborient.inventory.models.api.getdata.OneProductDataBase
import com.weborient.inventory.models.api.getdata.OneProductDataBody
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.models.api.getdata.ProductDataBase
import com.weborient.inventory.models.api.newproduct.NewProductGetDataResponse
import com.weborient.inventory.models.api.sendproduct.NewProductSendData
import com.weborient.inventory.models.api.sendproduct.NewProductSendDataResponse
import retrofit2.Call
import retrofit2.http.*

interface IApiRequests {
    /**
     * Adatok lekérdezése új termék felviteléhez
     */
    @GET("newproductgetdata")
    fun callNewProductGetData(): Call<NewProductGetDataResponse>

    /**
     * Minden termék lekérdezése
     */
    @GET("changequantitygetdata")
    fun callGetAllProducts(): Call<ProductDataBase>

    /**
     * Új termék felvitele
     */
    @POST("newproductsenddata")
    fun callNewProductSendData(@Body body: NewProductSendData): Call<NewProductSendDataResponse>

    /**
     * Adott termék adatainak lekérdezése
     */
    @POST("oneproductgetdata")
    fun callOneProductGetData(@Body body: OneProductDataBody): Call<OneProductDataBase>

    /**
     * Adott termék képeinek feltöltése
     */
    @POST("admin/product/{id}/image")
    fun callImageUpload(@Path("id") id: String): Call<Any?>
}