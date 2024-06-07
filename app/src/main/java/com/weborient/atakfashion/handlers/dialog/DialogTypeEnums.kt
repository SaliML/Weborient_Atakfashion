package com.weborient.atakfashion.handlers.dialog

enum class DialogTypeEnums(val value: Int) {
    Information(1),
    Successful(2),
    Warning(3),
    WarningClose(4),
    Error(5),
    ErrorClose(6),
    Question(7),
    QuestionDeleteUser(8),
    QuestionModifyUser(9),
    SettingsLocationProvider(10),
    SettingsNetwork(11),
    SettingsBluetooth(12),
    SettingsWifi(13)
}