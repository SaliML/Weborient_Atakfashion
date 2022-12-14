package com.weborient.inventory.handlers.api

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
     * Új termék felvitele
     */
    @POST("newproductsenddata")
    fun callNewProductSendData(@Body body: NewProductSendData): Call<NewProductSendDataResponse>
}