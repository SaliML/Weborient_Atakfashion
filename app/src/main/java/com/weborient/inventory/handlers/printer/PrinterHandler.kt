package com.weborient.inventory.handlers.printer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.brother.sdk.lmprinter.Channel
import com.brother.sdk.lmprinter.OpenChannelError
import com.brother.sdk.lmprinter.PrintError
import com.brother.sdk.lmprinter.PrinterDriverGenerator
import com.brother.sdk.lmprinter.setting.PTPrintSettings
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.qrcode.QRCodeHandler
import java.io.File
import java.io.FileOutputStream

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

    fun printImage(image: Bitmap, quantity: Int, deviceAddress: String?, bluetoothAdapter: BluetoothAdapter?): PrintResult{
        if(AppConfig.macAddress != null){
            if(bluetoothAdapter != null){
                //Csatorna megnyitása
                val channel = Channel.newBluetoothChannel(deviceAddress?.uppercase(), bluetoothAdapter)
                val result = PrinterDriverGenerator.openChannel(channel)

                when(result.error.code){
                    OpenChannelError.ErrorCode.NoError->{
                        //Nincs hiba, mehet a nyomtatás

                        //Fájlba írás
                        //val imageFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), AppConfig.imageFileName)

                        QRCodeHandler.compressBitmap(image, AppConfig.bitmapCompressFormat, AppConfig.bitmapCompressQuality, AppConfig.imageFile)

                        /*val fileOutputStream = FileOutputStream(AppConfig.imageFile)
                        image.compress(AppConfig.bitmapCompressFormat, AppConfig.bitmapCompressQuality, fileOutputStream)
                        fileOutputStream.flush()
                        fileOutputStream.close()*/

                        //Nyomtató beállítása és nyomtatás
                        val printerDriver = result.driver

                        val printSettings = PTPrintSettings(AppConfig.printerModel)
                        printSettings.labelSize = AppConfig.printerLabelSize
                        printSettings.workPath = AppConfig.printerWorkPath.path
                        printSettings.numCopies = quantity
                        printSettings.isChainPrint = AppConfig.printerChainPrint

                        //val printError = printerDriver.printImage(AppConfig.imageFile.absolutePath, printSettings)
                        val printError = printerDriver.printImage(image, printSettings)

                        printerDriver.closeChannel()

                        AppConfig.imageFile.delete()

                        if(printError.code == PrintError.ErrorCode.NoError){
                            //Sikeres nyomtatás

                            return PrintResult.Successful
                        }
                        else{
                            //Hiba történt a nyomtatáskor
                            return PrintResult.UnknownError
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
                        return PrintResult.UnknownError
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
}