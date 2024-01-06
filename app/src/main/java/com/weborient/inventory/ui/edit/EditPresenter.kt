package com.weborient.inventory.ui.edit

import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.models.api.getdata.GetDataByIDBase
import com.weborient.inventory.models.api.newproduct.ArrayElement
import com.weborient.inventory.models.api.sendproduct.ProductSendData

class EditPresenter(private val view: IEditContract.IEditView): IEditContract.IEditPresenter {
    private val interactor = EditInteractor(this)

    /**
     * Vissza gomb eseménye
     */
    override fun onClickedBackButton() {
        view.closeActivity()
    }

    /**
     * Szkennelés gomb eseménye
     */
    override fun onClickedScanButton() {
        view.navigateToScannerActivity()
    }

    override fun onClickedUploadButton(
        id: String?,
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

        if(id.isNullOrEmpty()){
            view.showIDError("Kötelező kitölteni!")
        }
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

                                                        interactor.uploadProduct(ProductSendData(name, description, category.id, packageType.id, tax.id, unit.id, status.id, template.id, tempQuantity, tempGrossPrice))
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

    override fun getItemByID(itemID: String?) {
        itemID?.let{
            interactor.getItemByID(it)
        }
    }

    /**
     * Termék adatainak visszaadása
     */
    override fun onFetchedProduct(product: GetDataByIDBase?, categories: ArrayList<ArrayElement>?, templates: ArrayList<ArrayElement>?,
    units: ArrayList<ArrayElement>?, packageTypes: ArrayList<ArrayElement>?,
    productstatuses: ArrayList<ArrayElement>?,
    taxes: ArrayList<ArrayElement>?) {
        if (product != null){
            if (product.text.isNullOrEmpty()){
                //Van ilyen termék
                view.hideScanButton()
                view.hideEmptyContainer()
                view.showProductDetailsContainer()

                if(product.datas.isNotEmpty()){
                    view.showProductDatas(product.datas.first(), categories, templates, units, packageTypes, productstatuses, taxes)
                }
            }
            else{
                //Nincs ilyen termék
                view.hideProductDetailsContainer()
                view.showScanButton()
                view.showEmptyContainer()
                view.showInformationDialog(product.text ?: "Nem található ilyen termék!", DialogTypeEnums.Error)
            }
        }
        else{
            view.hideProductDetailsContainer()
            view.showScanButton()
            view.showEmptyContainer()
            view.showInformationDialog("Nem található ilyen termék!", DialogTypeEnums.Error)
        }
    }

    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.SettingsNetwork->{
                view.showNetworkDialog()
            }
            else->{}
        }
    }

    override fun getDatas() {
        interactor.getDatas()
    }
}