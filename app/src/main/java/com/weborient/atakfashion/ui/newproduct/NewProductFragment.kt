package com.weborient.atakfashion.ui.newproduct

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
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.atakfashion.R
import com.weborient.atakfashion.databinding.FragmentNewItemBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.dialog.IDialogResultHandler
import com.weborient.atakfashion.handlers.service.PhoneServiceHandler
import com.weborient.atakfashion.models.api.newproduct.ArrayElement

class NewProductFragment : Fragment(), INewProductContract.INewProductView, IDialogResultHandler {
    private val presenter = NewProductPresenter(this)

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
    private lateinit var layoutTaxes: TextInputLayout
    private lateinit var layoutGrossPrice: TextInputLayout

    private lateinit var inputID: TextInputEditText
    private lateinit var inputName: TextInputEditText
    private lateinit var inputDescription: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var inputGrossPrice: TextInputEditText

    private lateinit var spinnerCategory: MaterialAutoCompleteTextView
    private lateinit var spinnerPresentation: MaterialAutoCompleteTextView
    private lateinit var spinnerUnit: MaterialAutoCompleteTextView
    private lateinit var spinnerStatus: MaterialAutoCompleteTextView
    private lateinit var spinnerTemplate: MaterialAutoCompleteTextView
    private lateinit var spinnerTax: MaterialAutoCompleteTextView

    private lateinit var buttonPrint: ImageView

    private lateinit var checkBoxPrintGroup: CheckBox

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null

    private var category: ArrayElement? = null
    private var presentation: ArrayElement? = null
    private var unit: ArrayElement? = null
    private var status: ArrayElement? = null
    private var template: ArrayElement? = null
    private var tax: ArrayElement? = null

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
        layoutTaxes = binding.tilNewItemTaxes
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
        spinnerTax = binding.autoNewItemTaxes

        buttonPrint = binding.ivNewItemPrint

        checkBoxPrintGroup = binding.cbNewItemPrintGroup

        binding.ivNewItemBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivNewItemUpload.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(requireContext())){
                presenter.onClickedUploadButton(inputName.text.toString(), inputDescription.text.toString(), inputQuantity.text.toString(),
                    category, presentation, unit, status, template, tax, inputGrossPrice.text.toString())
            }
            else{
                DialogHandler.showDialogWithResult(requireActivity(), this, getString(R.string.dialog_settings_network_state), DialogTypeEnums.SettingsNetwork)
            }
        }

        binding.ivNewItemPrint.setOnClickListener {
            if(PhoneServiceHandler.checkWifiState(requireContext())){
                if(checkBoxPrintGroup.isChecked){
                    //Csoportos nyomtatás esetén csak 1 db QR kódra van szükség
                    presenter.onClickedPrintButton(inputID.text.toString(), "1")
                }
                else{
                    //Nem csoportos nyomtatás esetén a felvett mennyiséget kell kinyomtatni
                    presenter.onClickedPrintButton(inputID.text.toString(), inputQuantity.text.toString())
                }
            }
            else{
                DialogHandler.showDialogWithResult(requireActivity(), this, getString(R.string.dialog_settings_bluetooth_state), DialogTypeEnums.SettingsBluetooth)
            }
        }

        spinnerCategory.setOnItemClickListener { adapterView, view, i, l ->
            category = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerPresentation.setOnItemClickListener { adapterView, view, i, l ->
            presentation = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerUnit.setOnItemClickListener { adapterView, view, i, l ->
            unit = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerStatus.setOnItemClickListener { adapterView, view, i, l ->
            status = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerTemplate.setOnItemClickListener { adapterView, view, i, l ->
            template = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerTax.setOnItemClickListener { adapterView, view, i, l ->
            tax = adapterView.getItemAtPosition(i) as ArrayElement
        }

        bluetoothManager = requireContext().getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        presenter.getDatas()
    }

    override fun setItemID(id: String?) {
        inputID.setText(id, TextView.BufferType.EDITABLE)
    }

    override fun setCategories(categories: ArrayList<ArrayElement>?) {
        categories?.let{
            spinnerCategory.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            spinnerCategory.setText(categories[0].name, false)
            category = categories[0]
        }
    }

    override fun setPackageTypes(packageTypes: ArrayList<ArrayElement>?) {
        packageTypes?.let{
            spinnerPresentation.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            spinnerPresentation.setText(packageTypes[0].name, false)
            presentation = packageTypes[0]
        }
    }

    override fun setUnits(units: ArrayList<ArrayElement>?) {
        units?.let{
            spinnerUnit.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            spinnerUnit.setText(units[0].name, false)
            unit = units[0]
        }
    }

    override fun setStatuses(statuses: ArrayList<ArrayElement>?) {
        statuses?.let{
            spinnerStatus.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))

            var element = statuses.firstOrNull {
                it.name.equals("raktaron", true) || it.name.equals("raktáron", true)
            }

            if(element != null){
                spinnerStatus.setText(statuses[statuses.indexOf(element)].name, false)
                status = statuses[statuses.indexOf(element)]
            }
            else{
                spinnerStatus.setText(statuses[0].name, false)
                status = statuses[0]
            }
        }
    }

    override fun setTemplates(templates: ArrayList<ArrayElement>?) {
        templates?.let{
            spinnerTemplate.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            spinnerTemplate.setText(templates[0].name, false)
            template = templates[0]
        }
    }

    override fun setTaxes(taxes: ArrayList<ArrayElement>?) {
        taxes?.let{
            spinnerTax.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, taxes))
            spinnerTax.setText(taxes[0].name, false)
            tax = taxes[0]
        }
    }

    override fun setGrossPrice(price: String) {
        inputGrossPrice.setText(price, TextView.BufferType.EDITABLE)
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(requireActivity(), information, type)
    }

    override fun showTimedInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showTimedDialog(requireActivity(), information, type)
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

    override fun showTaxError(error: String?) {
        layoutTaxes.error = error
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
        activity?.supportFragmentManager?.beginTransaction()?.remove(this@NewProductFragment)?.commit()
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
}