package com.weborient.atakfashion.models.user

enum class UserOperationResult(val value: Int) {
    Successful(0),
    UsernameEmpty(1),
    PasswordEmpty(2),
    PasswordConfirmEmpty(3),
    PasswordNotEquals(4),
    UserExists(5),
    UserNotSelected(6)
}