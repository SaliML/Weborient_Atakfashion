package com.weborient.inventory.ui.main

class MainPresenter(private val view: IMainContract.IMainView): IMainContract.IMainPresenter {
    /**
     * Bevétel gomb eseménye
     */
    override fun onClickedInButton() {
        view.navigateToInActivity()
    }

    /**
     * Kiadás gomb eseménye
     */
    override fun onClickedOutButton() {
        view.navigateToOutActivity()
    }

    /**
     * Beállítások gomb eseménye
     */
    override fun onClickedSettingsButton() {
        view.navigateToSettingsActivity()
    }

    /**
     * Kilépés gomb eseménye
     */
    override fun onClickedExitButton() {
        view.closeApplication()
    }
}