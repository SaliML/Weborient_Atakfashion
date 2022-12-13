package com.weborient.womo.handlers.api

interface IApiResponseHandler {
    fun onSuccessful(responseType: ApiCallType, result: Any?)
    fun onFailure(responseType: ApiCallType, result: Any?, throwable: Throwable?)
}