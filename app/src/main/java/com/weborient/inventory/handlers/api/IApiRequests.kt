package com.weborient.inventory.handlers.api

import com.weborient.inventory.models.api.addimage.AddImageResponse
import com.weborient.inventory.models.api.getdata.OneProductDataBase
import com.weborient.inventory.models.api.getdata.OneProductDataBody
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.models.api.getdata.ProductDataBase
import com.weborient.inventory.models.api.newproduct.NewProductGetDataResponse
import com.weborient.inventory.models.api.quantitychange.ProductQuantityChangeRequest
import com.weborient.inventory.models.api.quantitychange.ProductQuantityChangeResponse
import com.weborient.inventory.models.api.sendproduct.NewProductSendData
import com.weborient.inventory.models.api.sendproduct.NewProductSendDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
     * Adott termék mennyiségének módosítása
     */
    @POST("changequantitysenddata")
    fun callChangeQuantitySendData(@Body body: ProductQuantityChangeRequest): Call<ProductQuantityChangeResponse>

    /**
     * Adott termék képeinek feltöltése
     */
    @Multipart
    @POST("addimagesenddata")
    fun callAddImageSendData(@Part("id") id: RequestBody, @Part file: MultipartBody.Part): Call<AddImageResponse>
}