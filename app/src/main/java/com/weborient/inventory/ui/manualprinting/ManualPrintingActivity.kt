package com.weborient.inventory.ui.manualprinting

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toBitmapOrNull
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.weborient.inventory.R
import com.weborient.inventory.config.AppConfig
import com.weborient.inventory.databinding.ActivityManualPrintingBinding
import com.weborient.inventory.handlers.dialog.DialogHandler
import com.weborient.inventory.handlers.dialog.DialogResultEnums
import com.weborient.inventory.handlers.dialog.DialogTypeEnums
import com.weborient.inventory.handlers.dialog.IDialogResultHandler
import com.weborient.inventory.handlers.service.PhoneServiceHandler
import com.weborient.inventory.models.api.getdata.ProductData
import com.weborient.inventory.ui.`in`.IProductClickHandler
import com.weborient.inventory.ui.`in`.ProductListAdapter
import com.weborient.inventory.ui.manualprinting.enums.ManualPrintingOptions
import com.weborient.inventory.ui.scanner.ScannerActivity

class ManualPrintingActivity : AppCompatActivity(), IManualPrintingContract.IManualPrintingView, IDialogResultHandler,
    IProductClickHandler {
    private val presenter = ManualPrintingPresenter(this)

    /*private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothManager: BluetoothManager? = null*/
    private lateinit var recyclerItemList: RecyclerView

    private lateinit var scannerActivityLauncher: ActivityResultLauncher<Intent>

    private lateinit var layoutTextCustomText: TextInputLayout
    private lateinit var layoutQuantityCustomText: TextInputLayout
    private lateinit var layoutQRCodeEmptyCustomText: ConstraintLayout
    private lateinit var inputTextCustomText: TextInputEditText
    private lateinit var inputQuantityCustomText: TextInputEditText

    private lateinit var imageViewCustomText: ImageView

    //QR kód újranyomtatása
    private lateinit var imageViewReprintingQRCode: ImageView
    private lateinit var layoutQuantityReprintingQRCode: TextInputLayout
    private lateinit var inputQuantityReprintingQRCode: TextInputEditText
    private lateinit var layoutQRCodeEmptyReprintingQRCode: ConstraintLayout

    //QR kód nyomtatása termék alapján
    private lateinit var layoutProductAmount: LinearLayout
    private lateinit var layoutQuantityQRCodeProduct: TextInputLayout
    private lateinit var imageViewProductQRCode: ImageView

    private var selectedPrintingOption = ManualPrintingOptions.None

    private var productAdapter: ProductListAdapter? = null

    private var qrCode: Bitmap? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityManualPrintingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerItemList = binding.rvManualProductlist
        recyclerItemList.itemAnimator = null

        layoutProductAmount = binding.llManualAmount
        layoutQuantityQRCodeProduct = binding.tilManualQuantityProduct
        imageViewProductQRCode = binding.ivManualAmountQrCode

        layoutTextCustomText = binding.tilManualPrintingRawText
        layoutQuantityCustomText = binding.tilManualPrintingQuantityCustomText
        layoutQRCodeEmptyCustomText = binding.clManualQrcodeEmptyCustomText
        inputTextCustomText = binding.etManualPrintingText
        inputQuantityCustomText = binding.etManualPrintingQuantityCustomText
        imageViewCustomText = binding.ivManualPrintingQrCode

        layoutQuantityReprintingQRCode = binding.tilManualReprintingQuantity
        inputQuantityReprintingQRCode = binding.etManualReprintingQuantity
        imageViewReprintingQRCode = binding.ivManualReprintingQrCode
        layoutQRCodeEmptyReprintingQRCode = binding.clManualReprintingQrCodeEmpty

        //Opció választása
        binding.rbManualProduct.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                selectedPrintingOption = ManualPrintingOptions.ByProduct

                binding.svManualCustomText.visibility = View.GONE
                binding.svManualReprintingQrCode.visibility = View.GONE
                binding.swlManualRefresh.visibility = View.VISIBLE
                binding.llManualAmount.visibility = View.VISIBLE

                presenter.getProducts()
            }
        }

        binding.rbManualReprinting.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                selectedPrintingOption = ManualPrintingOptions.ByQRCode

                binding.swlManualRefresh.visibility = View.GONE
                binding.llManualAmount.visibility = View.GONE
                binding.svManualCustomText.visibility = View.GONE
                binding.svManualReprintingQrCode.visibility = View.VISIBLE
            }
        }

        binding.rbManualCustomText.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                selectedPrintingOption = ManualPrintingOptions.ByCustomText

                binding.svManualReprintingQrCode.visibility = View.GONE
                binding.llManualAmount.visibility = View.GONE
                binding.swlManualRefresh.visibility = View.GONE
                binding.svManualCustomText.visibility = View.VISIBLE
            }
        }

        binding.ivManualPrintProduct.setOnClickListener {
            presenter.onClickedPrintButton(qrCode, binding.etManualQuantityProduct.text.toString().toIntOrNull())
        }

        binding.ivManualPrintingBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.btManualPrintingGenerateCustomText.setOnClickListener {
            presenter.generateQRCode(inputTextCustomText.text.toString())
        }

        binding.btManualPrintingPrintCustomText.setOnClickListener {
            if(PhoneServiceHandler.checkWifiState(this)){
                presenter.onClickedPrintButton(imageViewCustomText.drawable?.toBitmapOrNull(), inputQuantityCustomText.text.toString().toIntOrNull())
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_wifi_state), DialogTypeEnums.SettingsWifi)
            }
        }

        binding.btManualReprintingPrint.setOnClickListener {
            if(PhoneServiceHandler.checkWifiState(this)){
                presenter.onClickedPrintButton(imageViewReprintingQRCode.drawable?.toBitmapOrNull(), inputQuantityReprintingQRCode.text.toString().toIntOrNull())
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_wifi_state), DialogTypeEnums.SettingsWifi)
            }
        }

        binding.btManualReprintingScan.setOnClickListener {
            presenter.onClickedScanButton()
        }

        scannerActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val itemID = it.data?.getStringExtra(AppConfig.SCANNING_RESULT)
                Toast.makeText(this, "Termékazonosító: ${itemID}", Toast.LENGTH_SHORT).show()
                presenter.generateQRCode(itemID)
            }
        }

        binding.swlManualRefresh.setOnRefreshListener {
            presenter.getProducts()
            binding.swlManualRefresh.isRefreshing = false
        }

        /*bluetoothManager = getSystemService(BluetoothManager::class.java)
        bluetoothAdapter = bluetoothManager?.adapter*/

        binding.rbManualCustomText.isChecked = true
    }

    override fun navigateToScannerActivity() {
        scannerActivityLauncher.launch(Intent(this, ScannerActivity::class.java))
    }

    override fun clearQRCode() {
        when(selectedPrintingOption) {
            ManualPrintingOptions.ByCustomText->{
                imageViewCustomText.visibility = View.GONE
                layoutQRCodeEmptyCustomText.visibility = View.VISIBLE
                imageViewCustomText.setImageDrawable(null)
            }
            ManualPrintingOptions.ByQRCode->{
                imageViewReprintingQRCode.setImageDrawable(null)
                imageViewReprintingQRCode.visibility = View.GONE
                layoutQRCodeEmptyReprintingQRCode.visibility = View.VISIBLE
            }
            ManualPrintingOptions.ByProduct->{
                qrCode = null
                imageViewProductQRCode.setImageDrawable(null)
            }
            else->{}
        }
    }

    override fun clearText() {
        inputTextCustomText.text?.clear()
    }

    override fun clearAmount() {
        inputQuantityCustomText.text?.clear()
        inputQuantityReprintingQRCode.text?.clear()
    }

    override fun setQRCode(bitmap: Bitmap) {
        when(selectedPrintingOption) {
            ManualPrintingOptions.ByCustomText->{
                imageViewCustomText.setImageBitmap(bitmap)
                imageViewCustomText.visibility = View.VISIBLE
                layoutQRCodeEmptyCustomText.visibility = View.GONE
            }
            ManualPrintingOptions.ByQRCode->{
                imageViewReprintingQRCode.setImageBitmap(bitmap)
                imageViewReprintingQRCode.visibility = View.VISIBLE
                layoutQRCodeEmptyReprintingQRCode.visibility = View.GONE
            }
            ManualPrintingOptions.ByProduct->{
                qrCode = bitmap
                imageViewProductQRCode.setImageBitmap(bitmap)
            }
            else->{}
        }
    }

    override fun showTextError(error: String?) {
        layoutTextCustomText.error = error
    }

    override fun showQuantityError(error: String?) {
        when(selectedPrintingOption) {
            ManualPrintingOptions.ByCustomText->{
                layoutQuantityCustomText.error = error
            }
            ManualPrintingOptions.ByQRCode->{
                layoutQuantityReprintingQRCode.error = error
            }
            ManualPrintingOptions.ByProduct->{
                layoutQuantityQRCodeProduct.error = error
            }
            else->{}
        }
    }

    override fun showBluetoothDialog() {
        startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
    }

    override fun showWifiDialog() {
        startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun showProgress(information: String) {
        DialogHandler.showProgressDialog(this, information)
    }

    override fun showItems(productList: ArrayList<ProductData>) {
        productAdapter = ProductListAdapter(this, this, productList)
        recyclerItemList.adapter = productAdapter
    }

    override fun hideProgress() {
        DialogHandler.closeProgressDialog()
    }

    override fun closeActivity() {
        finish()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }

    override fun onClickedProduct(product: ProductData?) {
        presenter.onClickedProduct(product)
    }
}