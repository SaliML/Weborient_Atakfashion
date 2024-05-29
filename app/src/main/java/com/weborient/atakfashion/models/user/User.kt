package com.weborient.atakfashion.models.user

data class User(var userName: String, var password: ByteArray, var permissions: ArrayList<UserPermission>)
