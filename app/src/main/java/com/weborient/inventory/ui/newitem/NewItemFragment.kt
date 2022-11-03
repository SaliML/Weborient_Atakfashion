package com.weborient.inventory.ui.newitem

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.R
import com.weborient.inventory.databinding.FragmentNewItemBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.dialog.IDialogResultHandler
import com.weborient.inventory.handlers.service.PhoneServiceHandler

class NewItemFragment : Fragment(), INewItemContract.INewItemView, IDialogResultHandler {
    private val presenter = NewItemPresenter(this)

    private lateinit var layoutProgress: ConstraintLayout
    private lateinit var layoutDatas: CardView
    private lateinit var layoutButtons: LinearLayout

    private lateinit var textProgressInformation: TextView

    private lateinit var layoutID: TextInputLayout
    private lateinit var layoutName: TextInputLayout
    private lateinit var layoutDescription: TextInputLayout
    private lateinit var layoutQuantity: TextInputLayout
    private lateinit var layoutCategory: TextInputLayout
    private lateinit var layoutPresentation: TextInputLayout
    private lateinit var layoutUnit: TextInputLayout
    private lateinit var layoutStatus: TextInputLayout
    private lateinit var layoutTemplate: TextInputLayout
    private lateinit var layoutGrossPrice: TextInputLayout

    private lateinit var inputID: TextInputEditText
    private lateinit var inputName: TextInputEditText
    private lateinit var inputDescription: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var inputNetPrice: TextInputEditText
    private lateinit var inputGrossPrice: TextInputEditText
    private lateinit var inputMargin: TextInputEditText

    private lateinit var spinnerCategory: MaterialAutoCompleteTextView
    private lateinit var spinnerPresentation: MaterialAutoCompleteTextView
    private lateinit var spinnerUnit: MaterialAutoCompleteTextView
    private lateinit var spinnerStatus: MaterialAutoCompleteTextView
    private lateinit var spinnerTemplate: MaterialAutoCompleteTextView
    private lateinit var spinnerTax: MaterialAutoCompleteTextView

    private lateinit var buttonPrint: ImageView

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null

    private var category: String? = null
    private var presentation: String? = null
    private var unit: String? = null
    private var status: String? = null
    private var template: String? = null
    private var tax: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewItemBinding.inflate(layoutInflater)

        layoutProgress = binding.clNewItemProgress
        layoutDatas = binding.cvNewItemData
        layoutButtons = binding.llNewItemButtons

        textProgressInformation = binding.tvNewItemProgressInformation

        layoutID = binding.tilNewItemId
        layoutName = binding.tilNewItemName
        layoutDescription = binding.tilNewItemDescription
        layoutQuantity = binding.tilNewItemQuantity
        layoutCategory = binding.tilNewItemCategory
        layoutPresentation = binding.tilNewItemPresentation
        layoutUnit = binding.tilNewItemUnit
        layoutStatus = binding.tilNewItemStatus
        layoutTemplate = binding.tilNewItemTemplate
        layoutGrossPrice = binding.tilNewItemGrossPrice

        inputID = binding.etNewItemId
        inputName = binding.etNewItemName
        inputDescription = binding.etNewItemDescription
        inputQuantity = binding.etNewItemQuantity
        inputGrossPrice = binding.etNewItemGrossPrice

        spinnerCategory = binding.autoNewItemCategory
        spinnerPresentation = binding.autoNewItemPresentation
        spinnerUnit = binding.autoNewItemUnit
        spinnerStatus = binding.autoNewItemStatus
        spinnerTemplate = binding.autoNewItemTemplate

        buttonPrint = binding.ivNewItemPrint

        binding.ivNewItemBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivNewItemUpload.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(requireContext())){
                presenter.onClickedUploadButton(inputName.text.toString(), inputDescription.text.toString(), inputQuantity.text.toString(),
                    category, presentation, unit, status, template, inputGrossPrice.text.toString())
            }
            else{
                DialogHandler.showDialogWithResult(requireActivity(), this, getString(R.string.dialog_settings_network_state), DialogTypeEnums.SettingsNetwork)
            }
        }

        binding.ivNewItemPrint.setOnClickListener {
            if(PhoneServiceHandler.checkBluetoothState(requireContext())){
                presenter.onClickedPrintButton(inputID.text.toString(), inputQuantity.text.toString(), bluetoothAdapter)
            }
            else{
                DialogHandler.showDialogWithResult(requireActivity(), this, getString(R.string.dialog_settings_bluetooth_state), DialogTypeEnums.SettingsBluetooth)
            }
        }

        spinnerCategory.setOnItemClickListener { adapterView, view, i, l ->
            category = adapterView.getItemAtPosition(i).toString()
        }

        spinnerPresentation.setOnItemClickListener { adapterView, view, i, l ->
            presentation = adapterView.getItemAtPosition(i).toString()
        }

        spinnerUnit.setOnItemClickListener { adapterView, view, i, l ->
            unit = adapterView.getItemAtPosition(i).toString()
        }

        spinnerStatus.setOnItemClickListener { adapterView, view, i, l ->
            status = adapterView.getItemAtPosition(i).toString()
        }

        spinnerTemplate.setOnItemClickListener { adapterView, view, i, l ->
            template = adapterView.getItemAtPosition(i).toString()
        }

        spinnerTax.setOnItemClickListener { adapterView, view, i, l ->
            tax = adapterView.getItemAtPosition(i).toString()
            calculate()
        }

        inputNetPrice.doAfterTextChanged {
            calculate()
        }

        inputMargin.doAfterTextChanged {
            calculate()
        }

        bluetoothManager = requireContext().getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        presenter.getCategories()
        presenter.getPresentation()
        presenter.getUnits()
        presenter.getStatuses()
        presenter.getTemplates()
        presenter.getTaxes()
    }

    override fun setItemID(id: String?) {
        inputID.setText(id, TextView.BufferType.EDITABLE)
    }

    override fun setCategories(categories: ArrayList<String>) {
        spinnerCategory.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, categories))
    }

    override fun setPresentations(presentations: ArrayList<String>) {
        spinnerPresentation.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, presentations))
    }

    override fun setUnits(units: ArrayList<String>) {
        spinnerUnit.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, units))
    }

    override fun setStatuses(statuses: ArrayList<String>) {
        spinnerStatus.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, statuses))
    }

    override fun setTemplates(templates: ArrayList<String>) {
        spinnerTemplate.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, templates))
    }

    override fun setTaxes(taxes: ArrayList<String>) {
        spinnerTax.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, taxes))
    }

    override fun setGrossPrice(price: String) {
        inputGrossPrice.setText(price, TextView.BufferType.EDITABLE)
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(requireActivity(), information, type)
    }

    override fun showNameError(error: String?) {
        layoutName.error = error
    }

    override fun showDescriptionError(error: String?) {
        layoutDescription.error = error
    }

    override fun showQuantityError(error: String?) {
        layoutQuantity.error = error
    }

    override fun showCategoryError(error: String?) {
        layoutCategory.error = error
    }

    override fun showPresentationError(error: String?) {
        layoutPresentation.error = error
    }

    override fun showUnitError(error: String?) {
        layoutUnit.error = error
    }

    override fun showStatusError(error: String?) {
        layoutStatus.error = error
    }

    override fun showTemplateError(error: String?) {
        layoutTemplate.error = error
    }

    override fun showGrossPriceError(error: String?) {
        layoutGrossPrice.error = error
    }

    override fun showPrintButton() {
        buttonPrint.visibility = View.VISIBLE
    }

    override fun showProgress(information: String) {
        DialogHandler.showProgressDialog(requireActivity(), information)
    }

    override fun hideProgress() {
        DialogHandler.closeProgressDialog()
    }

    override fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this@NewItemFragment)?.commit()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }

    override fun showBluetoothDialog() {
        startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
    }
    override fun showNetworkDialog() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    private fun calculate(){
        presenter.countGrossPrice(inputNetPrice.text.toString(), tax, inputMargin.text.toString())
    }
}