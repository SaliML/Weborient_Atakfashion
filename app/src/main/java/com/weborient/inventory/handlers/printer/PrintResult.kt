package com.weborient.inventory.handlers.printer

enum class PrintResult(val value: Int) {
    Successful(0),
    OpenStreamFailure(1),
    Timeout(2),
    MacAddressIsNull(3),
    BluetoothAdapterIsNull(4),
    UnknownError(5)
}