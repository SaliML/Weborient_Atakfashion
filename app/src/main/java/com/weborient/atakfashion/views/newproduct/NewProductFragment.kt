package com.weborient.atakfashion.views.newproduct

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
import com.weborient.atakfashion.models.api.sendproduct.NewProductSendDataResponse
import com.weborient.atakfashion.models.api.sendproduct.ProductSendData
import com.weborient.atakfashion.models.api.template.TemplateDataBase
import com.weborient.atakfashion.models.api.template.TemplateSendData
import com.weborient.atakfashion.ui.newproduct.INewProductContract
import com.weborient.atakfashion.ui.newproduct.NewProductPresenter
import com.weborient.atakfashion.viewmodels.newproduct.NewProductViewModel
import com.weborient.womo.handlers.api.ApiCallType


class NewProductFragment : Fragment(), INewProductContract.INewProductView, IDialogResultHandler {
    private val viewModel: NewProductViewModel by viewModels()
    private lateinit var binding: FragmentNewItemBinding
    private val presenter = NewProductPresenter(this)

    private var category: ArrayElement? = null
    private var presentation: ArrayElement? = null
    private var unit: ArrayElement? = null
    private var status: ArrayElement? = null
    private var template: ArrayElement? = null
    private var tax: ArrayElement? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewItemBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.ivNewItemBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivNewItemUpload.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(requireContext())){
                if(binding.etNewItemName.text.toString().isEmpty()){
                    showNameError("Kötelező kitölteni!")
                }
                else{
                    if(binding.etNewItemDescription.text.toString().isEmpty()){
                        showDescriptionError("Kötelező kitölteni!")
                    }
                    else{
                        if(binding.etNewItemQuantity.text.toString().isEmpty()){
                            showQuantityError("Kötelező kitölteni!")
                        }
                        else{
                            val tempQuantity = binding.etNewItemQuantity.text.toString().toIntOrNull()

                            if(tempQuantity == null){
                                showQuantityError("Kérem számot adjon meg!")
                            }
                            else{
                                if(category == null){
                                    showCategoryError("Kötelező kitölteni!")
                                }
                                else{
                                    if(presentation == null){
                                        showPresentationError("Kötelező kitölteni!")
                                    }
                                    else{
                                        if(unit == null){
                                            showUnitError("Kötelező kitölteni!")
                                        }
                                        else{
                                            if(status == null){
                                                showStatusError("Kötelező kitölteni!")
                                            }
                                            else{
                                                if(template == null){
                                                    showTemplateError("Kötelező kitölteni!")
                                                }
                                                else{
                                                    if(tax == null){
                                                        showTaxError("Kötelező kitölteni!")
                                                    }
                                                    else{
                                                        if(binding.etNewItemGrossPrice.text.toString().isEmpty()){
                                                            showGrossPriceError("Kötelező kitölteni!")
                                                        }
                                                        else{
                                                            val tempGrossPrice = binding.etNewItemGrossPrice.text.toString().toIntOrNull()

                                                            if(tempGrossPrice == null){
                                                                showGrossPriceError("Kérem számot adjon meg!")
                                                            }
                                                            else{

                                                                showNameError(null)
                                                                showDescriptionError(null)
                                                                showQuantityError(null)
                                                                showCategoryError(null)
                                                                showPresentationError(null)
                                                                showUnitError(null)
                                                                showStatusError(null)
                                                                showTemplateError(null)
                                                                showGrossPriceError(null)

                                                                viewModel.uploadProduct(
                                                                    ProductSendData(binding.etNewItemName.text.toString(), binding.etNewItemDescription.text.toString(), category!!.id, presentation!!.id, tax!!.id, unit!!.id, status!!.id, template!!.id, arrayListOf(), tempQuantity, tempGrossPrice)
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            else{
                DialogHandler.showDialogWithResult(requireActivity(), this, getString(R.string.dialog_settings_network_state), DialogTypeEnums.SettingsNetwork)
            }
        }

        binding.ivNewItemPrint.setOnClickListener {
            if(PhoneServiceHandler.checkWifiState(requireContext())){
                if(binding.cbNewItemPrintGroup.isChecked){
                    //Csoportos nyomtatás esetén csak 1 db QR kódra van szükség
                    presenter.onClickedPrintButton(binding.etNewItemId.text.toString(), "1")
                }
                else{
                    //Nem csoportos nyomtatás esetén a felvett mennyiséget kell kinyomtatni
                    presenter.onClickedPrintButton(binding.etNewItemId.text.toString(), binding.etNewItemQuantity.text.toString())
                }
            }
            else{
                DialogHandler.showDialogWithResult(requireActivity(), this, getString(R.string.dialog_settings_wifi_state), DialogTypeEnums.SettingsWifi)
            }
        }

        binding.autoNewItemCategory.setOnItemClickListener { adapterView, view, i, l ->
            category = adapterView.getItemAtPosition(i) as ArrayElement
        }

        binding.autoNewItemPresentation.setOnItemClickListener { adapterView, view, i, l ->
            presentation = adapterView.getItemAtPosition(i) as ArrayElement
        }

        binding.autoNewItemUnit.setOnItemClickListener { adapterView, view, i, l ->
            unit = adapterView.getItemAtPosition(i) as ArrayElement
        }

        binding.autoNewItemStatus.setOnItemClickListener { adapterView, view, i, l ->
            status = adapterView.getItemAtPosition(i) as ArrayElement
        }

        binding.autoNewItemTemplate.setOnItemClickListener { adapterView, view, i, l ->
            template = adapterView.getItemAtPosition(i) as ArrayElement

            template?.let {
                viewModel.getTemplateDatas(it.id)
            }
        }

        binding.autoNewItemTaxes.setOnItemClickListener { adapterView, view, i, l ->
            tax = adapterView.getItemAtPosition(i) as ArrayElement
        }

        /*viewModel.templateDatas.observe(viewLifecycleOwner) { templateDatas ->

            binding.llNewProductTemplate.removeAllViews()

            if(!templateDatas.isNullOrEmpty()){
                val titleTextView = TextView(requireContext())
                titleTextView.setGravity(Gravity.CENTER)
                titleTextView.setPadding(0,5,0,5)
                titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
                titleTextView.text = getString(R.string.in_title_template)
                binding.llNewProductTemplate.addView(titleTextView)

                templateDatas.forEach { data ->
                    val tempTextView = TextView(requireContext())
                    tempTextView.setGravity(Gravity.CENTER)
                    tempTextView.setPadding(0,5,0,5)
                    tempTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                    tempTextView.text = data.name
                    binding.llNewProductTemplate.addView(tempTextView)

                    data.data?.forEach { dataValue ->
                        val checkbox = CheckBox(requireContext())
                        checkbox.setGravity(Gravity.CENTER)
                        checkbox.setPadding(0,5,0,5)
                        checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)

                        if(dataValue.value.contains("#")){
                            checkbox.setBackgroundColor(Color.parseColor(dataValue.value))
                        }
                        else{
                            checkbox.text = "${dataValue.value}"
                        }
                        checkbox.setOnCheckedChangeListener { _, isChecked ->
                            if(isChecked){
                                viewModel.addTemplateData(data.id, dataValue)
                            }
                            else{
                                viewModel.removeTemplateData(data.id, dataValue)
                            }
                        }

                        binding.llNewProductTemplate.addView(checkbox)
                    }
                }

                binding.llNewProductTemplate.visibility = View.VISIBLE
            }
            else{
                binding.llNewProductTemplate.visibility = View.GONE
            }
        }*/

        viewModel.response.observe(viewLifecycleOwner){
            when(it.responseType){
                ApiCallType.GetTemplateData->{
                    if (!it.isSuccessful){
                        showInformationDialog("Hiba történt a sablon adatok lekérdezése során", DialogTypeEnums.Error)
                    }
                }
                ApiCallType.NewProductSendData->{
                    if(it.isSuccessful){
                        showInformationDialog("Sikeres felvitel", DialogTypeEnums.Successful)
                        binding.ivNewItemPrint.visibility = View.VISIBLE
                    }
                    else{
                        showInformationDialog("Hiba történt a termék felvétele során", DialogTypeEnums.Error)
                    }
                }
                else->{}
            }
        }

        viewModel.productID.observe(viewLifecycleOwner){
            binding.etNewItemId.setText(it, TextView.BufferType.EDITABLE)
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        presenter.getDatas()
    }

    override fun setItemID(id: String?) {
        binding.etNewItemId.setText(id, TextView.BufferType.EDITABLE)
    }

    override fun setCategories(categories: ArrayList<ArrayElement>?) {
        categories?.let{
            binding.autoNewItemCategory.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            binding.autoNewItemCategory.setText(categories[0].name, false)
            category = categories[0]
        }
    }

    override fun setPackageTypes(packageTypes: ArrayList<ArrayElement>?) {
        packageTypes?.let{
            binding.autoNewItemPresentation.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            binding.autoNewItemPresentation.setText(packageTypes[0].name, false)
            presentation = packageTypes[0]
        }
    }

    override fun setUnits(units: ArrayList<ArrayElement>?) {
        units?.let{
            binding.autoNewItemUnit.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            binding.autoNewItemUnit.setText(units[0].name, false)
            unit = units[0]
        }
    }

    override fun setStatuses(statuses: ArrayList<ArrayElement>?) {
        statuses?.let{
            binding.autoNewItemStatus.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))

            var element = statuses.firstOrNull {
                it.name.equals("raktaron", true) || it.name.equals("raktáron", true)
            }

            if(element != null){
                binding.autoNewItemStatus.setText(statuses[statuses.indexOf(element)].name, false)
                status = statuses[statuses.indexOf(element)]
            }
            else{
                binding.autoNewItemStatus.setText(statuses[0].name, false)
                status = statuses[0]
            }
        }
    }

    override fun setTemplates(templates: ArrayList<ArrayElement>?) {
        templates?.let{
            binding.autoNewItemTemplate.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it))
            binding.autoNewItemTemplate.setText(templates[0].name, false)
            template = templates[0]

            viewModel.getTemplateDatas(templates[0].id)
        }
    }

    override fun setTaxes(taxes: ArrayList<ArrayElement>?) {
        taxes?.let{
            binding.autoNewItemTaxes.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, taxes))
            binding.autoNewItemTaxes.setText(taxes[0].name, false)
            tax = taxes[0]
        }
    }

    override fun setGrossPrice(price: String) {
        binding.etNewItemGrossPrice.setText(price, TextView.BufferType.EDITABLE)
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(requireActivity(), information, type)
    }

    override fun showTimedInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showTimedDialog(requireActivity(), information, type)
    }

    override fun showNameError(error: String?) {
        binding.tilNewItemName.error = error
    }

    override fun showDescriptionError(error: String?) {
        binding.tilNewItemDescription.error = error
    }

    override fun showQuantityError(error: String?) {
        binding.tilNewItemQuantity.error = error
    }

    override fun showCategoryError(error: String?) {
        binding.tilNewItemCategory.error = error
    }

    override fun showPresentationError(error: String?) {
        binding.tilNewItemPresentation.error = error
    }

    override fun showUnitError(error: String?) {
        binding.tilNewItemUnit.error = error
    }

    override fun showStatusError(error: String?) {
        binding.tilNewItemStatus.error = error
    }

    override fun showTaxError(error: String?) {
        binding.tilNewItemTaxes.error = error
    }

    override fun showTemplateError(error: String?) {
        binding.tilNewItemTemplate.error = error
    }

    override fun showGrossPriceError(error: String?) {
        binding.tilNewItemGrossPrice.error = error
    }

    override fun showPrintButton() {
        binding.ivNewItemPrint.visibility = View.VISIBLE
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

    override fun showNetworkDialog() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}