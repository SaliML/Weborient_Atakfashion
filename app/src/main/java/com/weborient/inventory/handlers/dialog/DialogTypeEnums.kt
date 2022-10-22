package com.weborient.inventory.handlers.dialog

enum class DialogTypeEnums(val value: Int) {
    Information(1),
    Successful(2),
    Warning(3),
    Error(4),
    Question(5),
    SettingsLocationProvider(6),
    SettingsNetwork(7)
}