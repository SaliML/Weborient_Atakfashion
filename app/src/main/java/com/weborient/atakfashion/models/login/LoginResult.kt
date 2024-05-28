package com.weborient.atakfashion.models.login

enum class LoginResult(val value: Int) {
    Successful(1),
    UserNotExists(2),
    PasswordEmpty(3),
    UserEmpty(4),
    PasswordNotEquals(5)
}