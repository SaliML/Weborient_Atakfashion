package com.weborient.inventory.ui.out

import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.models.ItemModel
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.models.interfaces.IResponseDialogHandler

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
    }

    /**
     * Presenter interfésze
     */
    interface IOutPresenter: IResponseDialogHandler {
        fun onClickedBackButton()
        fun onClickedScanButton()
        fun onClickedDoneButton(amount: String)
        fun onFetchedItem(product: ProductData?)
        fun onResultDecreaseAmount(isSuccessful: Boolean)
        fun onDialogResult(result: DialogResultEnums)
        fun getItemByID(itemID: String?)
    }

    /**
     * Interactor interfésze
     */
    interface IOutInteractor{
        fun decreaseAmount(amount: Int)
        fun getItemByID(itemID: String)
    }
}