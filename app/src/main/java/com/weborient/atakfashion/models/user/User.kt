package com.weborient.atakfashion.models.user

data class User(val userName: String, val password: ByteArray, var permissions: ArrayList<UserPermission>)
