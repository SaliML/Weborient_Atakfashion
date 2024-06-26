package com.weborient.atakfashion.ui.edit

import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.models.api.getdata.GetDataByIDBase
import com.weborient.atakfashion.models.api.getdata.ProductDetails
import com.weborient.atakfashion.models.api.modifydata.ModifyDataByIDBody
import com.weborient.atakfashion.models.api.newproduct.ArrayElement
import com.weborient.atakfashion.models.interfaces.IResponseDialogHandler

interface IEditContract {
    interface IEditView{
        /**
         * Bezárás
         */
        fun closeActivity()

        /**
         * Navigálás a QR kód szkennelés felületre
         */
        fun navigateToScannerActivity()

        fun hideEmptyContainer()
        fun hideScanButton()
        fun showEmptyContainer()
        fun showScanButton()
        fun hideProductDetailsContainer()
        fun showProductDetailsContainer()
        fun showProductDatas(product: ProductDetails, categories: ArrayList<ArrayElement>?, templates: ArrayList<ArrayElement>?, units: ArrayList<ArrayElement>?, packageTypes: ArrayList<ArrayElement>?, productstatuses: ArrayList<ArrayElement>?, taxes: ArrayList<ArrayElement>?)
        fun showInformationDialog(information: String, type: DialogTypeEnums)
        fun showTimedInformationDialog(information: String, type: DialogTypeEnums)
        fun showIDError(error: String?)
        fun showNameError(error: String?)
        fun showDescriptionError(error: String?)
        fun showQuantityError(error: String?)
        fun showCategoryError(error: String?)
        fun showPresentationError(error: String?)
        fun showUnitError(error: String?)
        fun showStatusError(error: String?)
        fun showTemplateError(error: String?)
        fun showTaxError(error: String?)
        fun showGrossPriceError(error: String?)
        fun showNetworkDialog()
    }

    interface IEditPresenter: IResponseDialogHandler {
        /**
         * Vissza gomb eseménye
         */
        fun onClickedBackButton()

        /**
         * Beolvasás gomb eseménye
         */
        fun onClickedScanButton()

        /**
         * Feltöltés gomb eseménye
         */
        fun onClickedUploadButton(id: String?, name: String?, description: String?, quantity: String?, category: ArrayElement?, packageType: ArrayElement?, unit: ArrayElement?, status: ArrayElement?, template: ArrayElement?, tax: ArrayElement?, grossPrice: String?)

        /**
         * Termék lekérdezése
         */
        fun getItemByID(id: String?)

        /**
         * Termék adatainak visszaadása
         */
        fun onFetchedProduct(product: GetDataByIDBase?, categories: ArrayList<ArrayElement>?, templates: ArrayList<ArrayElement>?, units: ArrayList<ArrayElement>?, packageTypes: ArrayList<ArrayElement>?, productstatuses: ArrayList<ArrayElement>?, taxes: ArrayList<ArrayElement>?)

        fun onDialogResult(result: DialogResultEnums)
        fun getDatas()
    }

    interface IEditInteractor{
        fun getItemByID(id: String)
        fun getDatas()
        fun uploadProduct(product: ModifyDataByIDBody)
    }
}