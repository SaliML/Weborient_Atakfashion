package com.weborient.inventory.handlers.dialog

enum class DialogResultEnums(val value: Int) {
    Cancel(0),
    OK(1),
    SettingsLocationProvider(2),
    SettingsNetwork(3),
    SettingsBluetooth(4),
    SettingsLanguageHungarian(6),
    SettingsLanguageEnglish(7),
    SettingsLanguageGerman(8),
    PaymentCash(9),
    PaymentCard(10),
    ProceedToScanBarcode(11)
}