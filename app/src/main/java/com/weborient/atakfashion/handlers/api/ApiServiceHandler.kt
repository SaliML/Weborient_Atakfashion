package com.weborient.atakfashion.handlers.api

import com.weborient.womo.handlers.api.ApiCallType
import com.weborient.womo.handlers.api.IApiResponseHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

object ApiServiceHandler {
    fun <T> apiService(call: Call<T>, responseType: ApiCallType, handler: IApiResponseHandler, param: String? = null){
        call.enqueue(object: Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful){
                    handler.onSuccessful(responseType, response.body(), param)
                }
                else{
                    handler.onFailure(responseType, null, null, param)
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                if(t is ConnectException){
                    handler.onFailure(ApiCallType.Connection, null, t, param)
                }
                else if(t is IOException){
                    handler.onFailure(ApiCallType.Timeout, null, null, param)
                }
                else{
                    handler.onFailure(ApiCallType.Unknown, null, t, param)
                }
            }
        })
    }
}