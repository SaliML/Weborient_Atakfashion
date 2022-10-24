package com.weborient.inventory.ui.out

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.databinding.ActivityOutBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.ui.scanner.ScannerActivity

class OutActivity : AppCompatActivity(), IOutContract.IOutView {
    private val presenter = OutPresenter(this)

    private lateinit var containerEmpty: ConstraintLayout
    private lateinit var containerItem: ScrollView
    private lateinit var containerAmount: LinearLayout
    private lateinit var inputQuantity: EditText
    private lateinit var buttonDone: ImageView
    private lateinit var imageView: ImageView
    private lateinit var textID: TextView
    private lateinit var textName: TextView
    private lateinit var textDescription: TextView


    private lateinit var scannerActivityLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        containerEmpty = binding.clEmpty
        containerItem = binding.svItem
        containerAmount = binding.llOutAmount
        inputQuantity = binding.etOutQuantity
        buttonDone = binding.ivOutDone
        imageView = binding.ivOutPhoto
        textID = binding.tvOutItemId
        textName = binding.tvOutItemName
        textDescription = binding.tvOutItemDescription

        binding.ivOutBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivOutScan.setOnClickListener {
            presenter.onClickedScanButton()
        }

        binding.ivOutDone.setOnClickListener {
            presenter.onClickedDoneButton(inputQuantity.text.toString())
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

    override fun showItemDescription(itemDescription: String) {
        textDescription.text = itemDescription
    }

    override fun showItemPhoto(photoUrl: String) {
        imageView.let {
            val circularProgressDrawable = CircularProgressDrawable(this)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(this).load(photoUrl).placeholder(circularProgressDrawable).into(it)
        }
    }

    override fun showContainerEmpty() {
        containerEmpty.visibility = View.VISIBLE
    }

    override fun showContainerItem() {
        containerItem.visibility = View.VISIBLE
    }

    override fun showContainerAmount() {
        containerAmount.visibility = View.VISIBLE
    }

    override fun showButtonDone() {
        buttonDone.visibility = View.VISIBLE
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun hideContainerEmpty() {
        containerEmpty.visibility = View.GONE
    }

    override fun hideContainerItem() {
        containerItem.visibility = View.GONE
    }

    override fun hideContainerAmount() {
        containerAmount.visibility = View.GONE
    }

    override fun hideButtonDone() {
        buttonDone.visibility = View.GONE
    }
}