package com.weborient.inventory.handlers.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice

object PrinterHandler {
    @SuppressLint("MissingPermission")
    fun searchPrinter(pairedDevices: Set<BluetoothDevice>?, macAddress: String): BluetoothDevice?{
        val tempPrinter = pairedDevices?.find { device -> device.address.equals(macAddress, true) }

        if(tempPrinter != null){
            return tempPrinter
        }

        return null
    }
}