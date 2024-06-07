package com.weborient.atakfashion.models.user

/**
 * Felhasználót leíró osztály
 */
class User(var userName: String, var password: ByteArray, var permissions: ArrayList<UserPermission>)
{
    override fun toString(): String {
        return userName
    }
}
