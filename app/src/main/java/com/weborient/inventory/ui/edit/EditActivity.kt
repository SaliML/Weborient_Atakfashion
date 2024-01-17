package com.weborient.inventory.ui.edit

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.R
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.databinding.ActivityEditBinding
import com.weborient.inventory.databinding.ActivityOutBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.dialog.IDialogResultHandler
import com.weborient.inventory.handlers.service.PhoneServiceHandler
import com.weborient.inventory.models.api.getdata.ProductDetails
import com.weborient.inventory.models.api.newproduct.ArrayElement
import com.weborient.inventory.ui.scanner.ScannerActivity

class EditActivity : AppCompatActivity(), IEditContract.IEditView, IDialogResultHandler {
    private val presenter = EditPresenter(this)

    private lateinit var scannerActivityLauncher: ActivityResultLauncher<Intent>

    private lateinit var layoutEmpty: ConstraintLayout
    private lateinit var layoutProductDetails: ConstraintLayout
    private lateinit var buttonScan: ImageView

    private lateinit var layoutProgress: ConstraintLayout
    private lateinit var layoutDatas: ConstraintLayout

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

    private var selectedCategory: ArrayElement? = null
    private var selectedPresentation: ArrayElement? = null
    private var selectedUnit: ArrayElement? = null
    private var selectedStatus: ArrayElement? = null
    private var selectedTemplate: ArrayElement? = null
    private var selectedTax: ArrayElement? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutProgress = binding.clEditItemProgress
        layoutDatas = binding.clEditProductDetails
        textProgressInformation = binding.tvEditItemProgressInformation
        layoutID = binding.tilEditItemId
        layoutName = binding.tilEditItemName
        layoutDescription = binding.tilEditItemDescription
        layoutQuantity = binding.tilEditItemQuantity
        layoutCategory = binding.tilEditItemCategory
        layoutPresentation = binding.tilEditItemPresentation
        layoutUnit = binding.tilEditItemUnit
        layoutStatus = binding.tilEditItemStatus
        layoutTemplate = binding.tilEditItemTemplate
        layoutTaxes = binding.tilEditItemTaxes
        layoutGrossPrice = binding.tilEditItemGrossPrice

        inputID = binding.etEditItemId
        inputName = binding.etEditItemName
        inputDescription = binding.etEditItemDescription
        inputQuantity = binding.etEditItemQuantity
        inputGrossPrice = binding.etEditItemGrossPrice

        spinnerCategory = binding.autoEditItemCategory
        spinnerPresentation = binding.autoEditItemPresentation
        spinnerUnit = binding.autoEditItemUnit
        spinnerStatus = binding.autoEditItemStatus
        spinnerTemplate = binding.autoEditItemTemplate
        spinnerTax = binding.autoEditItemTaxes

        layoutEmpty = binding.clEditEmpty
        layoutProductDetails = binding.clEditProductDetails

        buttonScan = binding.ivEditScan


        binding.ivEditBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivEditScan.setOnClickListener {
            presenter.onClickedScanButton()
        }

        binding.ivEditItemUpload.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(this)){
                presenter.onClickedUploadButton(inputID.text.toString(), inputName.text.toString(), inputDescription.text.toString(), inputQuantity.text.toString(),
                    selectedCategory, selectedPresentation, selectedUnit, selectedStatus, selectedTemplate, selectedTax, inputGrossPrice.text.toString())
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_network_state), DialogTypeEnums.SettingsNetwork)
            }
        }

        scannerActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val itemID = it.data?.getStringExtra(AppConfig.SCANNING_RESULT)
                Toast.makeText(this, "Termékazonosító: ${itemID}", Toast.LENGTH_SHORT).show()
                presenter.getItemByID(itemID)
            }
        }

        spinnerCategory.setOnItemClickListener { adapterView, view, i, l ->
            selectedCategory = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerPresentation.setOnItemClickListener { adapterView, view, i, l ->
            selectedPresentation = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerUnit.setOnItemClickListener { adapterView, view, i, l ->
            selectedUnit = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerStatus.setOnItemClickListener { adapterView, view, i, l ->
            selectedStatus = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerTemplate.setOnItemClickListener { adapterView, view, i, l ->
            selectedTemplate = adapterView.getItemAtPosition(i) as ArrayElement
        }

        spinnerTax.setOnItemClickListener { adapterView, view, i, l ->
            selectedTax = adapterView.getItemAtPosition(i) as ArrayElement
        }
    }

    override fun onResume() {
        super.onResume()

        presenter.getDatas()
    }

    override fun navigateToScannerActivity() {
        scannerActivityLauncher.launch(Intent(this, ScannerActivity::class.java))
    }

    override fun hideEmptyContainer() {
        layoutEmpty.visibility = View.GONE
    }

    override fun hideScanButton() {
        buttonScan.visibility = View.GONE
    }

    override fun showEmptyContainer() {
        layoutEmpty.visibility = View.VISIBLE
    }

    override fun showScanButton() {
        buttonScan.visibility = View.VISIBLE
    }

    override fun hideProductDetailsContainer() {
        layoutDatas.visibility = View.GONE
    }

    override fun showProductDetailsContainer() {
        layoutDatas.visibility = View.VISIBLE
    }

    override fun showProductDatas(product: ProductDetails, categories: ArrayList<ArrayElement>?, templates: ArrayList<ArrayElement>?, units: ArrayList<ArrayElement>?, packageTypes: ArrayList<ArrayElement>?, productstatuses: ArrayList<ArrayElement>?, taxes: ArrayList<ArrayElement>?) {
        inputID.setText(product.id, TextView.BufferType.EDITABLE)
        inputName.setText(product.name, TextView.BufferType.EDITABLE)
        inputDescription.setText(product.description, TextView.BufferType.EDITABLE)
        inputQuantity.setText(product.quantity.toString(), TextView.BufferType.EDITABLE)
        inputGrossPrice.setText(product.grossprice.toString(), TextView.BufferType.EDITABLE)

        categories?.let{ categoryArray ->
            spinnerCategory.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, categoryArray))

            val tempCategory = categories.firstOrNull { category -> category.id == product.categoryId }

            if (tempCategory != null){
                spinnerCategory.setText(tempCategory.name, false)
                selectedCategory = tempCategory
            }
            else{
                spinnerCategory.setText(categories[0].name, false)
                selectedCategory = categories[0]
            }
        }

        templates?.let{ templateArray ->
            spinnerTemplate.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, templateArray))

            val tempTemplate = templates.firstOrNull { template -> template.id == product.templateId }

            if (tempTemplate != null){
                spinnerTemplate.setText(tempTemplate.name, false)
                selectedTemplate = tempTemplate
            }
            else{
                spinnerTemplate.setText(templates[0].name, false)
                selectedTemplate = templates[0]
            }
        }

        units?.let{ unitArray ->
            spinnerUnit.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, unitArray))

            val tempUnit = units.firstOrNull { unit -> unit.id == product.unitId }

            if (tempUnit != null){
                spinnerUnit.setText(tempUnit.name, false)
                selectedUnit = tempUnit
            }
            else{
                spinnerUnit.setText(units[0].name, false)
                selectedUnit = units[0]
            }
        }

        packageTypes?.let{ packageTypeArray ->
            spinnerPresentation.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, packageTypeArray))

            val tempPackageType = packageTypes.firstOrNull { presentation -> presentation.id == product.packageTypeId }

            if (tempPackageType != null){
                spinnerPresentation.setText(tempPackageType.name, false)
                selectedPresentation = tempPackageType
            }
            else{
                spinnerPresentation.setText(packageTypes[0].name, false)
                selectedPresentation = packageTypes[0]
            }
        }

        productstatuses?.let{ productStatusArray ->
            spinnerStatus.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, productStatusArray))

            val tempProductStatus = productstatuses.firstOrNull { productStatus -> productStatus.id == product.statusId }

            if (tempProductStatus != null){
                spinnerStatus.setText(tempProductStatus.name, false)
                selectedStatus = tempProductStatus
            }
            else{
                spinnerStatus.setText(productstatuses[0].name, false)
                selectedStatus = productstatuses[0]
            }
        }

        taxes?.let{ taxArray ->
            spinnerTax.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, taxArray))

            val tempTax = taxes.firstOrNull { tax -> tax.id == product.taxTypeId }

            if (tempTax != null){
                spinnerTax.setText(tempTax.name, false)
                selectedTax = tempTax
            }
            else{
                spinnerTax.setText(taxes[0].name, false)
                selectedTax = taxes[0]
            }
        }
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun showTimedInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showTimedDialog(this, information, type)
    }

    override fun showIDError(error: String?) {
        layoutID.error = error
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

    override fun showNetworkDialog() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    /**
     * Bezárás
     */
    override fun closeActivity() {
        finish()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }
}