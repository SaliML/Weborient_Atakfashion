package com.weborient.atakfashion.ui.`in`

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.printer.PrintResult
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.repositories.item.ItemRepository

class InPresenter(private val view: IInContract.IInView): IInContract.IInPresenter {
    private val interactor = InInteractor(this)

    override fun getItems() {
        interactor.getItems()
    }

    override fun onClickedUploadButton(quantity: String?) {
        if(ItemRepository.selectedProduct == null){
            view.showInformationDialog("Kérem válasszon ki egy terméket!", DialogTypeEnums.Warning)
        }
        else{
            if(quantity.isNullOrEmpty()){
                view.showQuantityError("Kötelező kitölteni!")
            }
            else {
                val tempQuantity = quantity.toIntOrNull()

                if (tempQuantity == null) {
                    view.showQuantityError("Kérem számot adjon meg!")
                } else {
                    view.showQuantityError(null)
                    interactor.uploadSelectedProduct(tempQuantity)
                }
            }
        }
    }

    override fun onClickedPrintButton(quantity: String?) {
        if(quantity.isNullOrEmpty()){
            view.showQuantityError("Kötelező kitölteni!")
        }
        else{
            val tempQuantity = quantity.toIntOrNull()

            if(tempQuantity == null){
                view.showQuantityError("Kérem számot adjon meg!")
            }
            else{
                view.showQuantityError(null)
                view.showProgress("Címke nyomtatása")
                interactor.printWifi(tempQuantity, AppConfig.ipAddress)
            }
        }
    }

    override fun onRetrievedItems(productList: ArrayList<ProductData>) {
        view.showItems(productList)
        view.hidePrintButton()
        view.clearQuantity()
    }

    override fun onClickedProduct(product: ProductData?) {
        interactor.setSelectedProduct(product)
    }

    override fun onSelectedProduct() {
        view.refreshList()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.SettingsBluetooth->{
                view.showBluetoothDialog()
            }
            DialogResultEnums.SettingsNetwork->{
                view.showNetworkDialog()
            }
            else->{}
        }
    }

    override fun onPrintResult(result: PrintResult) {
        view.hideProgress()

        when(result){
            PrintResult.Successful->{
                interactor.setSelectedProduct(null)
                view.hidePrintButton()
                view.clearQuantity()
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
            PrintResult.MissingProductID->{
                view.showInformationDialog("Nem található termék azonosító!", DialogTypeEnums.Error)
            }
            PrintResult.ConnectionUnknownError->{
                view.showInformationDialog("Ismeretlen hiba történt a csatlakozás során!", DialogTypeEnums.Error)
            }
            PrintResult.PrintUnknownError->{
                view.showInformationDialog("Ismeretlen hiba történt a nyomtatás során!", DialogTypeEnums.Error)
            }
            else->{
                view.showInformationDialog("Ismeretlen hiba történt!", DialogTypeEnums.Error)
            }
        }
    }

    override fun onUploadedResult(isSuccessful: Boolean) {
        view.showPrintButton()
    }

    override fun onSuccessful(information: String) {
        view.showInformationDialog(information, DialogTypeEnums.Successful)
    }

    override fun onFailure(information: String) {
        view.showInformationDialog(information, DialogTypeEnums.Error)
    }

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun onClickedAddButton() {
        view.showAddNewItemFragment()
    }
}