package com.weborient.inventory.handlers.dialog

enum class DialogResultEnums(val value: Int) {
    Cancel(0),
    OK(1),
    SettingsLocationProvider(2),
    SettingsNetwork(3),
    SettingsLanguageHungarian(4),
    SettingsLanguageEnglish(5),
    SettingsLanguageGerman(6),
    PaymentCash(7),
    PaymentCard(8),
    ProceedToScanBarcode(9)
}