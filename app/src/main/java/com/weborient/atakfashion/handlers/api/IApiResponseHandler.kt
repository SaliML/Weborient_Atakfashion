package com.weborient.womo.handlers.api

import com.weborient.atakfashion.handlers.api.ApiCallResponse

interface IApiResponseHandler {
    fun onResult(callResponse: ApiCallResponse)
    /*fun onSuccessful(responseType: ApiCallType, result: Any?, param: String? = null)
    fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?, param: String? = null)*/
}