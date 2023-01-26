package com.weborient.inventory.models

data class PermissionModel(
    val permission: String,
    val minSDKVersionCode: Int,
    val maxSDKVersionCode: Int?
)
