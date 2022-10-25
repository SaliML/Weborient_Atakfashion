package com.weborient.inventory.ui.manualprinting

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums

class ManualPrintingPresenter(private val view: IManualPrintingContract.IManualPrintingView): IManualPrintingContract.IManualPrintingPresenter {
    private val interactor = ManualPrintingInteractor(this)

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun onClickedGenerateButton(text: String?) {
        if(text.isNullOrEmpty()){
            view.showTextError("Kérem töltse ki a mezőt!")
        }
        else{
            view.showTextError(null)
            interactor.generateQRCodeFromText(text)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onClickedPrintButton(qrCode: Bitmap?, quantity: Int?, bluetoothAdapter: BluetoothAdapter?) {
        if(interactor.isPrinterPaired(bluetoothAdapter?.bondedDevices)){
            if(qrCode != null){
                if(quantity == null){
                    view.showQuantityError("Kérem adja meg a mennyiséget!")
                }
                else{
                    //Mehet a nyomtatás
                    view.showQuantityError(null)
                    interactor.print(qrCode, quantity, bluetoothAdapter, AppConfig.macAddress )
                }
            }
            else{
                view.showInformationDialog("Kérem generáljon QR kódot!", DialogTypeEnums.Warning)
            }
        }
        else{
            view.showInformationDialog("Kérem párosítsa a nyomtatót és próbálja újra!", DialogTypeEnums.Warning)
        }
    }

    override fun onGeneratedQRCode(bitmap: Bitmap) {
        view.showQRCode(bitmap)
    }

    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.SettingsBluetooth->{
                view.showBluetoothDialog()
            }
            else->{}
        }
    }
}