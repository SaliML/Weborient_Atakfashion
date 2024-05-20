package com.weborient.atakfashion.ui.out

import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.api.getdata.ProductData
import com.weborient.atakfashion.models.interfaces.IResponseDialogHandler

/**
 * MVP minta a kiadás felületre
 */
interface IOutContract {
    /**
     * View interfésze
     */
    interface IOutView{
        /**
         * Navigálás a QR kód olvasó felületre
         */
        fun navigateToScannerActivity()
        fun closeActivity()
        fun showItemID(itemID: String)
        fun showItemName(itemName: String)
        fun showItemPrice(itemPrice: String)
        fun showItemDescription(itemDescription: String)
        fun showItemPhoto(photoUrl: String?)
        fun showContainerEmpty()
        fun showContainerItem()
        fun showContainerAmount()
        fun showButtonDone()
        fun showQuantityError(error: String?)
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showNetworkDialog()
        fun hideContainerEmpty()
        fun hideContainerItem()
        fun hideContainerAmount()
        fun hideButtonDone()

        fun AddRemovableProductAndSave()
    }

    /**
     * Presenter interfésze
     */
    interface IOutPresenter: IResponseDialogHandler {
        fun onClickedBackButton()
        fun onClickedScanButton()
        fun onClickedDoneButton(amount: String, productID: String)
        fun onFetchedItem(product: ProductData?)
        fun onResultDecreaseAmount()
        fun onDialogResult(result: DialogResultEnums)
        fun getItemByID(itemID: String?)
    }

    /**
     * Interactor interfésze
     */
    interface IOutInteractor{
        fun decreaseQuantity(quantity: Int, productID: String)
        fun getItemByID(itemID: String)
        fun setTempProduct(product: ProductData)
    }
}