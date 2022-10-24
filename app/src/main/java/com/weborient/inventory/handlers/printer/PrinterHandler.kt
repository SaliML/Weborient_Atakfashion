package com.weborient.inventory.handlers.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

object PrinterHandler {
    @SuppressLint("MissingPermission")
    fun searchPrinter(pairedDevices: Set<BluetoothDevice>?, macAddress: String?): BluetoothDevice?{

        if(pairedDevices != null && macAddress != null){
            val deviceFound = pairedDevices.find { device -> device.address.equals(macAddress, true) }

            deviceFound?.let{ bluetoothDevice ->
                return bluetoothDevice
            }
        }

        return null
    }
}