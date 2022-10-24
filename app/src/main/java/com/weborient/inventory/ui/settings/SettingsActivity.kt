package com.weborient.inventory.ui.settings

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.databinding.ActivitySettingsBinding
import com.weborient.inventory.handlers.preferences.SharedPreferencesHandler

class SettingsActivity : AppCompatActivity(), ISettingsContract.ISettingsView {
    private val presenter = SettingsPresenter(this)

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null

    private lateinit var apiAddressView: EditText
    private lateinit var printerMacAddressView: EditText

    private lateinit var printerNameView: TextView
    private lateinit var printerStatusView: TextView
    private lateinit var appVersionView: TextView

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiAddressView = binding.etApiAddress
        printerMacAddressView = binding.etPrinterMacAddress

        printerNameView = binding.tvSettingsPrinterName
        printerStatusView = binding.tvSettingsPrinterStatus

        appVersionView = binding.tvSettingsVersion

        binding.ivSettingsBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivSettingsApiSave.setOnClickListener {
            presenter.onClickedApiSaveButton(apiAddressView.text.toString())
        }

        binding.ivSettingsPrinterMacAddressSave.setOnClickListener {
            presenter.onClickedPrinterMacAddressSaveButton(printerMacAddressView.text.toString())
        }

        binding.cvSettingsPrinterRefresh.setOnClickListener {
            presenter.refreshPrinter(bluetoothAdapter?.bondedDevices)
        }

        bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter

        presenter.getApiAddress()
        presenter.getMacAddress()
        presenter.getAppVersion()
        presenter.refreshPrinter(bluetoothAdapter?.bondedDevices)
    }

    override fun showApiAddress(apiAddress: String) {
        apiAddressView.setText(apiAddress, TextView.BufferType.EDITABLE)
    }

    override fun showPrinterMacAddress(printerMacAddress: String?) {
        printerMacAddressView.setText(printerMacAddress, TextView.BufferType.EDITABLE)
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

    override fun saveApiAddress(apiAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_API_ADDRESS, apiAddress)
    }

    @SuppressLint("MissingPermission")
    override fun saveMacAddress(macAddress: String) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_MAC_ADDRESS, macAddress)
        presenter.refreshPrinter(bluetoothAdapter?.bondedDevices)
    }

    override fun closeActivity() {
        finish()
    }
}