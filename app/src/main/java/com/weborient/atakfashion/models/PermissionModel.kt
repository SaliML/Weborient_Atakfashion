package com.weborient.atakfashion.models

data class PermissionModel(
    val permission: String,
    val minSDKVersionCode: Int,
    val maxSDKVersionCode: Int?
)
