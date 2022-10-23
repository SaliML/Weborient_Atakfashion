package com.weborient.inventory.repositories.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.weborient.inventory.handlers.printer.PrinterHandler
import com.weborient.inventory.models.PrinterModel

object PrinterRepository {
    var actualPrinter: PrinterModel? = null

    @SuppressLint("MissingPermission")
    fun setPrinter(pairedDevices: Set<BluetoothDevice>?){
        actualPrinter?.let{ printer->
            printer.macAddress?.let{ macAddress ->
                val device = PrinterHandler.searchPrinter(pairedDevices, macAddress)

                device?.let{
                    printer.name = it.name
                    printer.pairStatus = true
                }?:run{
                    printer.name = null
                    printer.pairStatus = false
                }
            }
        }
    }
}