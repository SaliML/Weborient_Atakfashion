package com.weborient.inventory.ui.manualprinting

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.graphics.Bitmap
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.printer.PrintResult

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

                    //view.showProgress("Címke nyomtatása")

                    interactor.print(qrCode, quantity, bluetoothAdapter, AppConfig.macAddress)
                }
            }
            else{
                view.showInformationDialog("Kérem generáljon QR kódot!", DialogTypeEnums.Warning)
            }
        }
        else{
            view.showInformationDialog("Kérem párosítsa a nyomtatót, ellenőrizze a beállításoknál a címet és próbálja újra!", DialogTypeEnums.Warning)
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

    override fun onPrintResult(result: PrintResult) {
        //view.hideProgress()

        when(result){
            PrintResult.Successful->{
                view.showInformationDialog("Sikeres nyomtatás!", DialogTypeEnums.Successful)
            }
            PrintResult.MacAddressIsNull->{
                view.showInformationDialog("Hiányzó fizikai cím, kérem a \"Beállítások\" felületen töltse ki a \"MAC\" címet!", DialogTypeEnums.Error)
            }
            PrintResult.OpenStreamFailure->{
                view.showInformationDialog("Nem sikerült csatlakozni a nyomtatóhoz, kérem ellenőrizze a nyomtató állapotát!", DialogTypeEnums.Error)
            }
            PrintResult.Timeout->{
                view.showInformationDialog("Időtúllépés, kérem ellenőrizze a nyomtató állapotát!", DialogTypeEnums.Error)
            }
            PrintResult.BluetoothAdapterIsNull->{
                view.showInformationDialog("Bluetooth adapter értéke NULL!", DialogTypeEnums.Error)
            }
            PrintResult.MissingProductID->{
                view.showInformationDialog("Hiányzó termékazonosító!", DialogTypeEnums.Error)
            }
            else->{
                view.showInformationDialog("Ismeretlen hiba történt a nyomtatás során!", DialogTypeEnums.Error)
            }
        }
    }
}