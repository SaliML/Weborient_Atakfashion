package com.weborient.inventory.handlers.dialog

enum class DialogTypeEnums(val value: Int) {
    Information(1),
    Successful(2),
    Warning(3),
    WarningClose(4),
    Error(5),
    ErrorClose(6),
    Question(7),
    SettingsLocationProvider(8),
    SettingsNetwork(9)

}