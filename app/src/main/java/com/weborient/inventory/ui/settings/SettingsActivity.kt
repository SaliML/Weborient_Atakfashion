package com.weborient.inventory.ui.settings

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.databinding.ActivitySettingsBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.preferences.SharedPreferencesHandler

class SettingsActivity : AppCompatActivity(), ISettingsContract.ISettingsView {
    private val presenter = SettingsPresenter(this)

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null

    private lateinit var layoutApiAddress: TextInputLayout
    private lateinit var layoutPrinterMacAddress: TextInputLayout
    private lateinit var layoutPrinterIPAddress: TextInputLayout

    private lateinit var apiAddressView: EditText
    private lateinit var printerMacAddressView: EditText
    private lateinit var printerIPAddressView: EditText

    private lateinit var printerNameView: TextView
    private lateinit var printerStatusView: TextView
    private lateinit var appVersionView: TextView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchIsAutoCut: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchIsCutAndEnd: Switch

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchIsAutoCut = binding.swSettingsAutoCut
        switchIsCutAndEnd = binding.swSettingsCutAtEnd

        layoutApiAddress = binding.tilSettingsApi
        layoutPrinterIPAddress = binding.tilSettingsApi
        //layoutPrinterMacAddress = binding.tilSettingsMac

        apiAddressView = binding.etApiAddress
        printerIPAddressView = binding.etSettingsPrinterIpAddress
        //printerMacAddressView = binding.etSettingsPrinterMacAddress

        /*printerNameView = binding.tvSettingsPrinterName
        printerStatusView = binding.tvSettingsPrinterStatus*/

        appVersionView = binding.tvSettingsVersion

        binding.ivSettingsBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.btSettingsApiSave.setOnClickListener {
            presenter.onClickedApiSaveButton(apiAddressView.text.toString())
        }

        binding.btSettingsPrinterSettingsSave.setOnClickListener {
            presenter.onClickedPrinterSettingsSaveButton(printerIPAddressView.text.toString(), binding.swSettingsAutoCut.isChecked, binding.swSettingsCutAtEnd.isChecked)

        }

        /*binding.btSettingsPrinterRefresh.setOnClickListener {
            presenter.refreshPrinter(bluetoothAdapter?.bondedDevices)
        }*/

        /*bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter*/

        presenter.getApiAddress()
        presenter.getIPAddress()
        presenter.getCutSettings()
        //presenter.getMacAddress()
        presenter.getAppVersion()
        //presenter.refreshPrinter(bluetoothAdapter?.bondedDevices)
    }

    override fun showApiAddress(apiAddress: String) {
        apiAddressView.setText(apiAddress, TextView.BufferType.EDITABLE)
    }

    override fun showPrinterMacAddress(printerMacAddress: String?) {
        printerMacAddressView.setText(printerMacAddress, TextView.BufferType.EDITABLE)
    }

    override fun showPrinterIPAddress(printerIPAddress: String?) {
        printerIPAddressView.setText(printerIPAddress, TextView.BufferType.EDITABLE)
    }

    override fun showPrinterName(printerName: String) {
        printerNameView.text = printerName
    }

    override fun showPrinterStatus(printerStatus: String) {
        printerStatusView.text = printerStatus
    }

    override fun showAppVersion(version: String) {
        appVersionView.text = version
    }

    override fun showApiError(error: String?) {
        layoutApiAddress.error = error
    }

    override fun showMacAddressError(error: String?) {
        layoutPrinterMacAddress.error = error
    }

    override fun showIPAddressError(error: String?) {
        layoutPrinterIPAddress.error = error
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showTimedDialog(this, information, type)
    }

    override fun showCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean) {
        switchIsAutoCut.isChecked = isAutoCut
        switchIsCutAndEnd.isChecked = isCutAtEnd
    }

    override fun saveApiAddress(apiAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_API_ADDRESS, apiAddress)
    }

    @SuppressLint("MissingPermission")
    override fun saveMacAddress(macAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_MAC_ADDRESS, macAddress)
        presenter.refreshPrinter(bluetoothAdapter?.bondedDevices)
    }

    override fun saveIPAddress(ipAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_IP_ADDRESS, ipAddress)
    }

    override fun saveCutSettings(isAutoCut: Boolean, isCutAtEnd: Boolean) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_CUT_AT_END, isCutAtEnd)
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_AUTO_CUT, isAutoCut)
    }

    override fun closeActivity() {
        finish()
    }
}