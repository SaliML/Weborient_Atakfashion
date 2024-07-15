package com.weborient.atakfashion.ui.newproduct

import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.printer.PrintResult
import com.weborient.atakfashion.models.api.newproduct.ArrayElement
import com.weborient.atakfashion.models.api.sendproduct.ProductSendData
import com.weborient.atakfashion.models.api.template.TemplateSendData
import kotlinx.coroutines.*

class NewProductPresenter(private val view: INewProductContract.INewProductView): INewProductContract.INewProductPresenter, CoroutineScope by MainScope() {
    private val interactor = NewProductInteractor(this)

    override fun getDatas() {
        interactor.getDatas()
    }

    override fun onClickedBackButton() {
        view.closeFragment()
    }

    override fun onClickedUploadButton(
        name: String?,
        description: String?,
        quantity: String?,
        category: ArrayElement?,
        packageType: ArrayElement?,
        unit: ArrayElement?,
        status: ArrayElement?,
        template: ArrayElement?,
        tax: ArrayElement?,
        grossPrice: String?,
    ) {
        view.showNameError(null)
        view.showDescriptionError(null)
        view.showQuantityError(null)
        view.showCategoryError(null)
        view.showPresentationError(null)
        view.showUnitError(null)
        view.showStatusError(null)
        view.showTemplateError(null)

        if(name.isNullOrEmpty()){
            view.showNameError("Kötelező kitölteni!")
        }
        else{
            if(description.isNullOrEmpty()){
                view.showDescriptionError("Kötelező kitölteni!")
            }
            else{
                if(quantity.isNullOrEmpty()){
                    view.showQuantityError("Kötelező kitölteni!")
                }
                else{
                    val tempQuantity = quantity.toIntOrNull()

                    if(tempQuantity == null){
                        view.showQuantityError("Kérem számot adjon meg!")
                    }
                    else{
                        if(category == null){
                            view.showCategoryError("Kötelező kitölteni!")
                        }
                        else{
                            if(packageType == null){
                                view.showPresentationError("Kötelező kitölteni!")
                            }
                            else{
                                if(unit == null){
                                    view.showUnitError("Kötelező kitölteni!")
                                }
                                else{
                                    if(status == null){
                                        view.showStatusError("Kötelező kitölteni!")
                                    }
                                    else{
                                        if(template == null){
                                            view.showTemplateError("Kötelező kitölteni!")
                                        }
                                        else{
                                            if(tax == null){
                                                view.showTaxError("Kötelező kitölteni!")
                                            }
                                            else{
                                                if(grossPrice.isNullOrEmpty()){
                                                    view.showGrossPriceError("Kötelező kitölteni!")
                                                }
                                                else{
                                                    val tempGrossPrice = grossPrice.toIntOrNull()

                                                    if(tempGrossPrice == null){
                                                        view.showGrossPriceError("Kérem számot adjon meg!")
                                                    }
                                                    else{
                                                        //Mehet a feltöltés
                                                        view.showNameError(null)
                                                        view.showDescriptionError(null)
                                                        view.showQuantityError(null)
                                                        view.showCategoryError(null)
                                                        view.showPresentationError(null)
                                                        view.showUnitError(null)
                                                        view.showStatusError(null)
                                                        view.showTemplateError(null)
                                                        view.showGrossPriceError(null)

                                                        interactor.uploadProduct(ProductSendData(name, description, category.id, packageType.id, tax.id, unit.id, status.id, template.id, arrayListOf(TemplateSendData("RUHAK", arrayListOf("362", "363"))), tempQuantity, tempGrossPrice))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onClickedPrintButton(
        id: String,
        quantity: String?
    ) {
        if(quantity.isNullOrEmpty()){
            view.showQuantityError("Kötelező kitölteni!")
        }
        else{
            val tempQuantity = quantity.toIntOrNull()

            if(tempQuantity == null){
                view.showQuantityError("Kérem számot adjon meg!")
            }
            else{
                view.showProgress("Címke nyomtatása")

                interactor.printWifi(id, tempQuantity, AppConfig.ipAddress)
            }
        }
    }

    override fun onUploadedResult(isSuccessful: Boolean, id: String?) {
        view.showInformationDialog("Sikeres feltöltés!", DialogTypeEnums.Successful)
        view.setItemID(id)
        view.showProgress("Termék feltöltése")
        view.showPrintButton()
    }

    override fun onRetrievedCategories(categories: ArrayList<ArrayElement>?) {
        view.setCategories(categories)
    }

    override fun onRetrievedPackageTypes(packageTypes: ArrayList<ArrayElement>?) {
        view.setPackageTypes(packageTypes)
    }

    override fun onRetrievedUnits(units: ArrayList<ArrayElement>?) {
        view.setUnits(units)
    }

    override fun onRetrievedStatuses(statuses: ArrayList<ArrayElement>?) {
        view.setStatuses(statuses)
    }

    override fun onRetrievedTemplates(templates: ArrayList<ArrayElement>?) {
        view.setTemplates(templates)
    }

    override fun onRetrievedTaxes(taxes: ArrayList<ArrayElement>?) {
        view.setTaxes(taxes)
    }

    override fun onReceivedNewProductID(id: String) {
        view.setItemID(id)
        view.showInformationDialog("Sikeres feltöltés!", DialogTypeEnums.Successful)
        view.showPrintButton()
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
            else->{
                view.showInformationDialog("Ismeretlen hiba történt a nyomtatás során!", DialogTypeEnums.Error)
            }
        }
    }

    override fun onSuccessful(information: String) {
        view.showTimedInformationDialog(information, DialogTypeEnums.Successful)
    }

    override fun onFailure(information: String) {
        view.showInformationDialog(information, DialogTypeEnums.Error)
    }
}