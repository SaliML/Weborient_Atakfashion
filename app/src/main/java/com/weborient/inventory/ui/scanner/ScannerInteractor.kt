package com.weborient.womo.ui.scanner

class ScannerInteractor(private val presenter: IScannerContract.IScannerPresenter): IScannerContract.IScannerInteractor {
    override fun findItemByID(text: String) {
        //teszt
        presenter.onFoundItem()
    }
}