package com.weborient.atakfashion.models.user

/**
 * Felhasználói jogosultságokat leíró osztály
 */
enum class UserPermission(val value: Int) {
    In(1),
    Out(2),
    Removal(3),
    Edit(4),
    ManualPrinting(5),
    Photos(6),
    Users(7),
    Settings(8)
}