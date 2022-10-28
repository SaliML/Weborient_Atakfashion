package com.weborient.inventory.ui.newitem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.R
import com.weborient.inventory.databinding.FragmentNewItemBinding
import com.weborient.inventory.handlers.dialog.DialogTypeEnums

class NewItemFragment : Fragment(), INewItemContract.INewItemView {
    private val presenter = NewItemPresenter(this)

    private lateinit var layoutID: TextInputLayout
    private lateinit var layoutName: TextInputLayout
    private lateinit var layoutDescription: TextInputLayout
    private lateinit var layoutQuantity: TextInputLayout
    private lateinit var layoutCategory: TextInputLayout
    private lateinit var layoutPresentation: TextInputLayout
    private lateinit var layoutUnit: TextInputLayout
    private lateinit var layoutStatus: TextInputLayout
    private lateinit var layoutTemplate: TextInputLayout
    private lateinit var layoutTax: TextInputLayout
    private lateinit var layoutNetPrice: TextInputLayout
    private lateinit var layoutGrossPrice: TextInputLayout
    private lateinit var layoutMargin: TextInputLayout

    private lateinit var inputID: TextInputEditText
    private lateinit var inputName: TextInputEditText
    private lateinit var inputDescription: TextInputEditText
    private lateinit var inputQuantity: TextInputEditText
    private lateinit var inputNetPrice: TextInputEditText
    private lateinit var inputGrossPrice: TextInputEditText
    private lateinit var inputMargin: TextInputEditText

    private lateinit var buttonPrint: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewItemBinding.inflate(layoutInflater)

        layoutID = binding.tilNewItemId
        layoutName = binding.tilNewItemName
        layoutDescription = binding.tilNewItemDescription
        layoutQuantity = binding.tilNewItemQuantity
        layoutCategory = binding.tilNewItemCategory
        layoutPresentation = binding.tilNewItemPresentation
        layoutUnit = binding.tilNewItemUnit
        layoutStatus = binding.tilNewItemStatus
        layoutTemplate = binding.tilNewItemTemplate
        layoutTax = binding.tilNewItemTax
        layoutNetPrice = binding.tilNewItemNetPrice
        layoutGrossPrice = binding.tilNewItemGrossPrice
        layoutMargin = binding.tilNewItemMargin

        inputID = binding.etNewItemId
        inputName = binding.etNewItemName
        inputDescription = binding.etNewItemDescription
        inputQuantity = binding.etNewItemQuantity
        inputNetPrice = binding.etNewItemNetPrice
        inputGrossPrice = binding.etNewItemGrossPrice
        inputMargin = binding.etNewItemMargin

        buttonPrint = binding.ivNewItemPrint

        binding.ivNewItemBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivNewItemUpload.setOnClickListener {

        }

        binding.ivNewItemPrint.setOnClickListener {

        }

        return binding.root
    }

    override fun setItemID(id: String) {
        TODO("Not yet implemented")
    }

    override fun setCategories(categories: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun setPresentations(presentation: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun setUnits(units: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun setStatuses(statuses: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun setTemplates(templates: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun setTaxes(taxes: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        TODO("Not yet implemented")
    }

    override fun showNameError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showDescriptionError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showQuantityError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showCategoryError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showPresentationError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showUnitError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showStatusError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showTaxError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showNetPriceError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showTemplateError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showMarginError(error: String?) {
        TODO("Not yet implemented")
    }

    override fun showPrintButton() {
        TODO("Not yet implemented")
    }

    override fun closeFragment() {
        activity?.supportFragmentManager?.beginTransaction()?.remove(this@NewItemFragment)?.commit()
    }
}