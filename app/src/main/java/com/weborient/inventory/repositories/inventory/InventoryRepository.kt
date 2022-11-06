package com.weborient.inventory.repositories.inventory

object InventoryRepository {
    var categories = arrayListOf("Blézerek", "Ruhák", "Farmerek", "Kiegészítők", "Mellény", "Overálok", "Nadrágok", "Szoknyák", "Felsők és pulóverek")
    var presentations = arrayListOf("db", "doboz", "pár", "raklap", "tekercs")
    var units = arrayListOf("cm", "db", "fm", "kg", "l", "m", "m2", "pár")
    var statuses = arrayListOf("Készleten")
    var templates = arrayListOf("Farmerek", "Virág")
}