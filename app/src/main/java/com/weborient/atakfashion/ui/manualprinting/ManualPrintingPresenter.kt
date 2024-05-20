package com.weborient.atakfashion.ui.manualprinting

import android.annotation.SuppressLint
import android.graphics.Bitmap
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.printer.PrintResult
import com.weborient.atakfashion.models.api.getdata.ProductData

class ManualPrintingPresenter(private val view: IManualPrintingContract.IManualPrintingView): IManualPrintingContract.IManualPrintingPresenter {
    private val interactor = ManualPrintingInteractor(this)

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun generateQRCode(text: String?) {
        if(text.isNullOrEmpty()){
            view.showTextError("Kérem adja meg a generálni kívánt szöveget vagy olvasson be QR kódot!")
        }
        else{
            view.showTextError(null)
            interactor.generateQRCodeFromText(text)
        }
    }

    override fun getProducts() {
        interactor.getProducts()
    }

    @SuppressLint("MissingPermission")
    /*override fun onClickedPrintButton(qrCode: Bitmap?, quantity: Int?, bluetoothAdapter: BluetoothAdapter?) {
        if(interactor.isPrinterPaired(bluetoothAdapter?.bondedDevices)){
            if(qrCode != null){
                if(quantity == null){
                    view.showQuantityError("Kérem adja meg a mennyiséget!")
                }
                else{
                    //Mehet a nyomtatás
                    view.showQuantityError(null)

                    //view.showProgress("Címke nyomtatása")

                    interactor.printBluetooth(qrCode, quantity, bluetoothAdapter, AppConfig.macAddress)
                }
            }
            else{
                view.showInformationDialog("Kérem generáljon QR kódot!", DialogTypeEnums.Warning)
            }
        }
        else{
            view.showInformationDialog("Kérem párosítsa a nyomtatót, ellenőrizze a beállításoknál a címet és próbálja újra!", DialogTypeEnums.Warning)
        }
    }*/

    override fun onClickedPrintButton(qrCode: Bitmap?, quantity: Int?) {
        if(qrCode != null){
            if(quantity == null){
                view.showQuantityError("Kérem adja meg a mennyiséget!")
            }
            else{
                //Mehet a nyomtatás
                view.showQuantityError(null)

                //view.showProgress("Címke nyomtatása")

                interactor.printWifi(qrCode, quantity, AppConfig.ipAddress)
            }
        }
        else{
            view.showInformationDialog("Kérem generáljon vagy olvassa be a QR kódot!", DialogTypeEnums.Warning)
        }
    }

    override fun onClickedScanButton() {
        view.navigateToScannerActivity()
    }

    override fun onGeneratedQRCode(bitmap: Bitmap) {
        view.setQRCode(bitmap)
    }

    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.SettingsBluetooth->{
                view.showBluetoothDialog()
            }
            DialogResultEnums.SettingsWifi->{
                view.showWifiDialog()
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

        view.clearQRCode()
        view.clearText()
        view.clearAmount()
    }

    override fun onSuccessful(information: String) {
        view.showInformationDialog(information, DialogTypeEnums.Successful)
    }

    override fun onFailure(information: String) {
        view.showInformationDialog(information, DialogTypeEnums.Error)
    }

    override fun onClickedProduct(product: ProductData?) {
        if(product != null){
            interactor.generateQRCodeFromText(product.id)
        }
        else{
            view.showInformationDialog("Nincs kijelölve termék!", DialogTypeEnums.Warning)
        }
    }

    override fun onRetrievedProducts(productList: ArrayList<ProductData>) {
        view.showItems(productList)
    }
}