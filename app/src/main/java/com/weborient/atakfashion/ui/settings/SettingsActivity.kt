package com.weborient.atakfashion.ui.settings

import android.R
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.brother.sdk.lmprinter.setting.QLPrintSettings
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.databinding.ActivitySettingsBinding
import com.weborient.atakfashion.databinding.ActivityUsersBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.preferences.SharedPreferencesHandler
import com.weborient.atakfashion.models.QLPrinterLabelType
import com.weborient.atakfashion.viewmodels.settings.SettingsViewModel
import com.weborient.atakfashion.viewmodels.users.UsersViewModel

class SettingsActivity : AppCompatActivity(), ISettingsContract.ISettingsView {
    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    private val presenter = SettingsPresenter(this)

    private lateinit var layoutApiAddress: TextInputLayout
    private lateinit var layoutPrinterIPAddress: TextInputLayout

    private lateinit var apiAddressView: EditText
    private lateinit var printerIPAddressView: EditText

    private lateinit var printerNameView: TextView
    private lateinit var printerStatusView: TextView
    private lateinit var appVersionView: TextView

    private lateinit var spinnerLabelSizes: MaterialAutoCompleteTextView

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchIsAutoCut: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var switchIsCutAndEnd: Switch

    private var selectedLabelSize: QLPrinterLabelType? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, com.weborient.atakfashion.R.layout.activity_settings)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        switchIsAutoCut = binding.swSettingsAutoCut
        switchIsCutAndEnd = binding.swSettingsCutAtEnd

        layoutApiAddress = binding.tilSettingsApi
        layoutPrinterIPAddress = binding.tilSettingsApi
        spinnerLabelSizes = binding.autoSettingsLabelType

        apiAddressView = binding.etApiAddress
        printerIPAddressView = binding.etSettingsPrinterIpAddress

        appVersionView = binding.tvSettingsVersion

        binding.ivSettingsBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.btSettingsApiSave.setOnClickListener {
            presenter.onClickedApiSaveButton(apiAddressView.text.toString())
        }

        binding.btSettingsPrinterSettingsSave.setOnClickListener {
            presenter.onClickedPrinterSettingsSaveButton(printerIPAddressView.text.toString(), binding.swSettingsAutoCut.isChecked, binding.swSettingsCutAtEnd.isChecked, selectedLabelSize)

        }

        binding.btSettingsStoredDataExport.setOnClickListener {
            viewModel.exportStoredData(this)
        }

        binding.btSettingsStoredDataImport.setOnClickListener {
            viewModel.importStoredData(this)
        }

        spinnerLabelSizes.setOnItemClickListener { adapterView, view, i, l ->
            selectedLabelSize = adapterView.getItemAtPosition(i) as QLPrinterLabelType
        }

        presenter.getApiAddress()
        presenter.getIPAddress()
        presenter.getCutSettings()
        presenter.getAppVersion()
    }

    override fun onResume() {
        super.onResume()

        presenter.getSavedLabelSize()
    }

    override fun setLabelSizes(labelSizes: ArrayList<String>) {
        spinnerLabelSizes.setAdapter(ArrayAdapter(this, R.layout.simple_list_item_1, labelSizes))
    }

    override fun showApiAddress(apiAddress: String) {
        apiAddressView.setText(apiAddress, TextView.BufferType.EDITABLE)
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

    override fun savePrinterLabel(labelID: Int) {
        SharedPreferencesHandler.saveValue(this, AppConfig.SHAREDPREF_ID, AppConfig.SHAREDPREF_KEY_PRINTER_LABEL_ID, labelID)
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

    override fun selectLabelSize(savedLabelType: QLPrintSettings.LabelSize?, labelTypes: ArrayList<QLPrinterLabelType>) {
        labelTypes.let{ labelTypeArray ->
            spinnerLabelSizes.setAdapter(ArrayAdapter(this, R.layout.simple_list_item_1, labelTypeArray))

            val tempLabelSize = labelTypes.firstOrNull { labelType -> labelType.id == savedLabelType }

            if (tempLabelSize != null){
                spinnerLabelSizes.setText(tempLabelSize.name, false)
                selectedLabelSize = tempLabelSize
            }
            else{
                spinnerLabelSizes.setText(labelTypes[0].name, false)
                selectedLabelSize = labelTypes[0]
            }
        }
    }
}