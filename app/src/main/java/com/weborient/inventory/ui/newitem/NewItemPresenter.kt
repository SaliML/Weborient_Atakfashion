package com.weborient.inventory.ui.newitem

import com.weborient.inventory.handlers.dialog.DialogTypeEnums

class NewItemPresenter(private val view: INewItemContract.INewItemView): INewItemContract.INewItemPresenter {
    override fun onClickedBackButton() {
        view.closeFragment()
    }

    override fun onClickedUploadButton(
        name: String?,
        description: String?,
        quantity: String?,
        category: String?,
        presentation: String?,
        unit: String?,
        status: String?,
        template: String?,
        tax: String?,
        netPrice: String?,
        margin: String?
    ) {
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
                        if(category.isNullOrEmpty()){
                            view.showCategoryError("Kötelező kitölteni!")
                        }
                        else{
                            if(presentation.isNullOrEmpty()){
                                view.showPresentationError("Kötelező kitölteni!")
                            }
                            else{
                                if(unit.isNullOrEmpty()){
                                    view.showUnitError("Kötelező kitölteni!")
                                }
                                else{
                                    if(status.isNullOrEmpty()){
                                        view.showStatusError("Kötelező kitölteni!")
                                    }
                                    else{
                                        if(template.isNullOrEmpty()){
                                            view.showTemplateError("Kötelező kitölteni!")
                                        }
                                        else{
                                            if(tax.isNullOrEmpty()){
                                                view.showTaxError("Kötelező kitölteni!")
                                            }
                                            else{
                                                if(netPrice.isNullOrEmpty()){
                                                    view.showNetPriceError("Kötelező kitölteni!")
                                                }
                                                else{
                                                    val tempNetPrice = netPrice.toIntOrNull()

                                                    if(tempNetPrice == null){
                                                        view.showNetPriceError("Kérem számot adjon meg!")
                                                    }
                                                    else{
                                                        if(margin.isNullOrEmpty()){
                                                            view.showMarginError("Kötelező kitölteni!")
                                                        }
                                                        else{
                                                            val tempMargin = margin.toIntOrNull()

                                                            if(tempMargin == null){
                                                                view.showMarginError("Kérem számot adjon meg!")
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
                                                                view.showTaxError(null)
                                                                view.showNetPriceError(null)
                                                                view.showMarginError(null)

                                                                view.setItemID("12345")
                                                                view.showPrintButton()
                                                                view.showInformationDialog("Sikeres feltöltés!", DialogTypeEnums.Successful)

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
        }
    }

    override fun onClickedPrintButton(quantity: String) {
        TODO("Not yet implemented")
    }

    override fun onUploadedResult(isSuccessful: Boolean, id: String?) {
        TODO("Not yet implemented")
    }

    override fun onRetrievedCategories(category: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onRetrievedPresentations(presentation: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onRetrievedUnits(units: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onRetrievedStatuses(statuses: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onRetrievedTemplates(templates: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onRetrievedTaxes(taxes: ArrayList<String>) {
        TODO("Not yet implemented")
    }
}