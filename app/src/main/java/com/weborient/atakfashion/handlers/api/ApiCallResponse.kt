package com.weborient.atakfashion.handlers.api

import com.weborient.womo.handlers.api.ApiCallType

data class ApiCallResponse(val isSuccessful: Boolean, val responseType: ApiCallType, val result: Any?, val throwable: Throwable?, val param: String? = null)
