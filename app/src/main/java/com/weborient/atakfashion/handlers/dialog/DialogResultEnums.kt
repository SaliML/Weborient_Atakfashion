package com.weborient.atakfashion.handlers.dialog

enum class DialogResultEnums(val value: Int) {
    Cancel(0),
    OK(1),
    SettingsLocationProvider(2),
    SettingsNetwork(3),
    SettingsBluetooth(4),
    SettingsWifi(5),
    SettingsLanguageHungarian(6),
    SettingsLanguageEnglish(7),
    SettingsLanguageGerman(8),
    PaymentCash(9),
    PaymentCard(10),
    ProceedToScanBarcode(11),
    DeleteUserOk(12),
    ModifyUserOk(13),
    ExitOk(14)
}