package com.weborient.womo.handlers.api

interface IApiResponseHandler {
    fun onSuccessful(responseType: ApiCallType, result: Any?, param: String? = null)
    fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?, param: String? = null)
}