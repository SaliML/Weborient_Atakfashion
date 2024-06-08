package com.weborient.atakfashion.ui.out

import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.api.getdata.ProductData

class OutPresenter(private val view: IOutContract.IOutView): IOutContract.IOutPresenter {
    private val interactor = OutInteractor(this)

    override fun onClickedBackButton() {
        view.closeActivity()
    }

    override fun onClickedScanButton() {
        view.navigateToScannerActivity()
    }

    override fun onClickedDoneButton(amount: String, productID: String) {
        val quantity = amount.toIntOrNull()

        if(quantity != null){
            if(quantity > 0){
                view.showQuantityError(null)
                interactor.decreaseQuantity(quantity, productID)
            }
            else{
                view.showQuantityError("Kérem 0-nál nagyobb számot adjon meg!")
            }
        }
        else{
            view.showQuantityError("Kérem számot adjon meg!")
        }
    }

    override fun getItemByID(itemID: String?) {
        itemID?.let{
            interactor.getItemByID(it)
        }
    }

    override fun onSuccessful(information: String) {
        if(information.contains("hiba", true)){
            view.showInformationDialog(information, DialogTypeEnums.Warning)
        }
        else{
            view.showInformationDialog(information, DialogTypeEnums.Successful)
        }
    }

    override fun onFailure(information: String) {
        view.showInformationDialog(information, DialogTypeEnums.Error)
    }

    override fun onFetchedItem(product: ProductData?) {
        if(product != null){
            interactor.setTempProduct(product)

            //Adatok megjelenítése a VIEW-ban ...
            view.showItemPhoto(product.pictureURL)
            view.showItemID(product.id)
            view.showItemName(product.name)
            view.showItemDescription(product.description)
            view.showItemPrice(product.grossprice.toString())

            //Termék konténer megjelenítése
            view.showContainerItem()
            view.hideContainerEmpty()

            //Elvonás gomb megjelenítése
            view.showButtonDone()

            //Mennyiség konténer megjelenítése
            view.showContainerAmount()
        }
        else{
            //Termék konténer megjelenítése
            view.showContainerEmpty()
            view.hideContainerItem()

            //Elvonás gomb megjelenítése
            view.hideButtonDone()

            //Mennyiség konténer megjelenítése
            view.hideContainerAmount()
        }
    }

    override fun onResultDecreaseAmount() {
        view.showContainerEmpty()
        view.hideContainerItem()
        view.hideContainerAmount()
        view.hideButtonDone()

        //view.AddRemovableProductAndSave()
    }

    override fun addRemovableProduct() {
        view.AddRemovableProductAndSave()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        when(result){
            DialogResultEnums.SettingsNetwork->{
                view.showNetworkDialog()
            }
            else->{}
        }
    }
}