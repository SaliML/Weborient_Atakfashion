package com.weborient.atakfashion.ui.out

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputLayout
import com.weborient.atakfashion.R
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.databinding.ActivityOutBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.dialog.IDialogResultHandler
import com.weborient.atakfashion.handlers.service.PhoneServiceHandler
import com.weborient.atakfashion.repositories.RemovaledItemRepository
import com.weborient.atakfashion.ui.scanner.ScannerActivity

class OutActivity : AppCompatActivity(), IOutContract.IOutView, IDialogResultHandler {
    private val presenter = OutPresenter(this)

    private lateinit var layoutEmpty: ConstraintLayout
    private lateinit var layoutItem: ScrollView
    private lateinit var layoutQuantity: LinearLayout
    private lateinit var layoutInput: TextInputLayout
    private lateinit var inputQuantity: EditText
    private lateinit var buttonDone: ImageView
    private lateinit var imageView: ImageView
    private lateinit var textID: TextView
    private lateinit var textName: TextView
    private lateinit var textPrice: TextView
    private lateinit var textDescription: TextView

    private lateinit var scannerActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutEmpty = binding.clEmpty
        layoutItem = binding.svItem
        layoutQuantity = binding.llOutAmount
        layoutInput = binding.tilOutQuantity
        inputQuantity = binding.etOutQuantity
        buttonDone = binding.ivOutDone
        imageView = binding.ivOutPhoto
        textID = binding.tvOutItemId
        textName = binding.tvOutItemName
        textPrice = binding.tvOutItemPrice
        textDescription = binding.tvOutItemDescription

        binding.ivOutBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivOutScan.setOnClickListener {
            presenter.onClickedScanButton()
        }

        binding.ivOutDone.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(this)){
                presenter.onClickedDoneButton(inputQuantity.text.toString(), textID.text.toString())
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
    }

    override fun navigateToScannerActivity() {
        scannerActivityLauncher.launch(Intent(this, ScannerActivity::class.java))
    }

    override fun closeActivity() {
        finish()
    }

    override fun showItemID(itemID: String) {
        textID.text = itemID
    }

    override fun showItemName(itemName: String) {
        textName.text = itemName
    }

    override fun showItemPrice(itemPrice: String) {
        textPrice.text = itemPrice
    }

    override fun showItemDescription(itemDescription: String) {
        textDescription.text = itemDescription
    }

    override fun showItemPhoto(photoUrl: String?) {
        imageView.let {
            Glide.with(this).load(photoUrl).placeholder(R.drawable.image_not_available).into(it)
        }
    }

    override fun showContainerEmpty() {
        layoutEmpty.visibility = View.VISIBLE
    }

    override fun showContainerItem() {
        layoutItem.visibility = View.VISIBLE
    }

    override fun showContainerAmount() {
        layoutQuantity.visibility = View.VISIBLE
    }

    override fun showButtonDone() {
        buttonDone.visibility = View.VISIBLE
    }

    override fun showQuantityError(error: String?) {
        layoutInput.error = error
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun hideContainerEmpty() {
        layoutEmpty.visibility = View.GONE
    }

    override fun hideContainerItem() {
        layoutItem.visibility = View.GONE
    }

    override fun hideContainerAmount() {
        layoutQuantity.visibility = View.GONE
        inputQuantity.text.clear()
    }

    override fun hideButtonDone() {
        buttonDone.visibility = View.GONE
    }

    override fun AddRemovableProductAndSave() {
        RemovaledItemRepository.AddRemovaledProduct(this)
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }

    override fun showNetworkDialog() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }
}