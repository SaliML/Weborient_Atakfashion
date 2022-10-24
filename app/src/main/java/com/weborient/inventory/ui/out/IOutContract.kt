package com.weborient.inventory.ui.out

import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.models.ItemModel

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
        fun showItemPhoto(photoUrl: String)
        fun showContainerEmpty()
        fun showContainerItem()
        fun showContainerAmount()
        fun showButtonDone()
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun hideContainerEmpty()
        fun hideContainerItem()
        fun hideContainerAmount()
        fun hideButtonDone()

    }

    /**
     * Presenter interfésze
     */
    interface IOutPresenter{
        fun onClickedBackButton()
        fun onClickedScanButton()
        fun onClickedDoneButton(amount: String)
        fun getItemByID(itemID: String?)
        fun onFetchedItem(item: ItemModel?)
        fun onResultDecreaseAmount(isSuccessful: Boolean)
    }

    /**
     * Interactor interfésze
     */
    interface IOutInteractor{
        fun decreaseAmount(amount: Int)
        fun getItemByID(itemID: String)
    }
}