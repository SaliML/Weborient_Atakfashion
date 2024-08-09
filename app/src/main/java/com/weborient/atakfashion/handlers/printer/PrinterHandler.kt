package com.weborient.atakfashion.handlers.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.brother.sdk.lmprinter.Channel
import com.brother.sdk.lmprinter.OpenChannelError
import com.brother.sdk.lmprinter.PrintError
import com.brother.sdk.lmprinter.PrinterDriverGenerator
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.qrcode.QRCodeHandler

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

    fun printImageBluetooth(image: Bitmap, quantity: Int, deviceAddress: String?, bluetoothAdapter: BluetoothAdapter?): PrintResult{
        if(AppConfig.macAddress != null){
            if(bluetoothAdapter != null){
                //Csatorna megnyitása
                val channel = Channel.newBluetoothChannel(deviceAddress?.uppercase(), bluetoothAdapter)
                val result = PrinterDriverGenerator.openChannel(channel)

                when(result.error.code){
                    OpenChannelError.ErrorCode.NoError->{
                        //Nincs hiba, mehet a nyomtatás

                        QRCodeHandler.compressBitmap(image, AppConfig.bitmapCompressFormat, AppConfig.bitmapCompressQuality, AppConfig.imageFile)

                        //Nyomtató beállítása és nyomtatás
                        val printerDriver = result.driver

                        val printSettings = QLPrintSettings(AppConfig.printerModel)
                        printSettings.labelSize = AppConfig.printerLabelSize
                        printSettings.workPath = AppConfig.printerWorkPath.path
                        printSettings.numCopies = quantity
                        printSettings.isAutoCut = AppConfig.isAutoCut
                        printSettings.isCutAtEnd = AppConfig.isCutAtEnd

                        val printError = printerDriver.printImage(image, printSettings)

                        printerDriver.closeChannel()

                        AppConfig.imageFile.delete()

                        if(printError.code == PrintError.ErrorCode.NoError){
                            //Sikeres nyomtatás

                            return PrintResult.Successful
                        }
                        else{
                            //Hiba történt a nyomtatáskor
                            return PrintResult.PrintUnknownError
                        }
                    }
                    OpenChannelError.ErrorCode.OpenStreamFailure->{
                        //Sikertelen kapcsolat felépítés
                        return PrintResult.OpenStreamFailure
                    }
                    OpenChannelError.ErrorCode.Timeout->{
                        //Időtúllépés
                        return PrintResult.Timeout
                    }
                    else->{
                        //Ismeretlen hiba
                        return PrintResult.ConnectionUnknownError
                    }
                }
            }
            else{
                return PrintResult.BluetoothAdapterIsNull
            }
        }
        else{
            return PrintResult.MacAddressIsNull
        }
    }

    fun printImageWifi(image: Bitmap, quantity: Int, deviceIPAddress: String?): PrintResult{
        QRCodeHandler.compressBitmap(image, AppConfig.bitmapCompressFormat, AppConfig.bitmapCompressQuality, AppConfig.imageFile)
        if(AppConfig.ipAddress != null){
            //Csatorna megnyitása
            val channel = Channel.newWifiChannel(deviceIPAddress)
            val result = PrinterDriverGenerator.openChannel(channel)

            when(result.error.code){
                OpenChannelError.ErrorCode.NoError->{
                    //Nincs hiba, mehet a nyomtatás

                    QRCodeHandler.compressBitmap(image, AppConfig.bitmapCompressFormat, AppConfig.bitmapCompressQuality, AppConfig.imageFile)

                    //Nyomtató beállítása és nyomtatás
                    val printerDriver = result.driver

                    val printSettings = QLPrintSettings(AppConfig.printerModel)
                    printSettings.labelSize = AppConfig.printerLabelSize
                    printSettings.workPath = AppConfig.printerWorkPath.path
                    printSettings.numCopies = quantity
                    printSettings.isAutoCut = AppConfig.isAutoCut
                    printSettings.isCutAtEnd = AppConfig.isCutAtEnd

                    val printError = printerDriver.printImage(image, printSettings)

                    printerDriver.closeChannel()

                    AppConfig.imageFile.delete()

                    if(printError.code == PrintError.ErrorCode.NoError){
                        //Sikeres nyomtatás

                        return PrintResult.Successful
                    }
                    else{
                        //Hiba történt a nyomtatáskor
                        return PrintResult.PrintUnknownError
                    }
                }
                OpenChannelError.ErrorCode.OpenStreamFailure->{
                    //Sikertelen kapcsolat felépítés
                    return PrintResult.OpenStreamFailure
                }
                OpenChannelError.ErrorCode.Timeout->{
                    //Időtúllépés
                    return PrintResult.Timeout
                }
                else->{
                    //Ismeretlen hiba
                    return PrintResult.ConnectionUnknownError
                }
            }
        }
        else{
            return PrintResult.IPAddressIsNull
        }
    }
}