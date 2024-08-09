package com.weborient.atakfashion.handlers.printer

enum class PrintResult(val value: Int) {
    Successful(0),
    OpenStreamFailure(1),
    Timeout(2),
    MacAddressIsNull(3),
    BluetoothAdapterIsNull(4),
    ConnectionUnknownError(5),
    PrintUnknownError(6),
    MissingProductID(7),
    IPAddressIsNull(8)
}