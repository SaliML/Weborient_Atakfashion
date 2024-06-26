package com.weborient.atakfashion.ui.photos

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.weborient.atakfashion.R
import com.weborient.atakfashion.config.AppConfig
import com.weborient.atakfashion.databinding.ActivityPhotosBinding
import com.weborient.atakfashion.handlers.dialog.DialogHandler
import com.weborient.atakfashion.handlers.dialog.DialogResultEnums
import com.weborient.atakfashion.handlers.dialog.DialogTypeEnums
import com.weborient.atakfashion.handlers.dialog.IDialogResultHandler
import com.weborient.atakfashion.handlers.file.FileHandler
import com.weborient.atakfashion.handlers.format.FormatHandler
import com.weborient.atakfashion.handlers.service.PhoneServiceHandler
import com.weborient.atakfashion.models.PhotoListAdapter
import com.weborient.atakfashion.models.PhotoUploadModel
import com.weborient.atakfashion.models.interfaces.IPhotoClickHandler
import com.weborient.atakfashion.models.photo.PhotoItem
import com.weborient.atakfashion.ui.scanner.ScannerActivity
import java.io.File
import java.util.Calendar


class PhotosActivity : AppCompatActivity(), IPhotosContract.IPhotosView, IDialogResultHandler,
    IPhotoClickHandler {
    private val presenter = PhotoPresenter(this)
    private var imageName: String? = null

    private lateinit var layoutQRCodeEmpty: ConstraintLayout
    private lateinit var imageQRCodeView: ImageView

    private lateinit var photoListView: RecyclerView

    private lateinit var scannerActivityLauncher: ActivityResultLauncher<Intent>
    private lateinit var photoActivityLauncher: ActivityResultLauncher<Intent>

    private var photoListAdapter: PhotoListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhotosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutQRCodeEmpty = binding.clQrcodeEmpty
        imageQRCodeView = binding.ivPhotosQrCode

        photoListView = binding.rvPhotos
        photoListView.itemAnimator = null

        binding.ivPhotosBack.setOnClickListener {
            presenter.onClickedBackButton()
        }

        binding.ivPhotosScan.setOnClickListener {
            presenter.onClickedScanButton()
        }

        binding.ivPhotosTakePhoto.setOnClickListener {
            presenter.onClickedTakePhotoButton()
        }

        binding.ivPhotosAddPhoto.setOnClickListener{
            //val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult(intent,1)

            val intent = Intent()
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.setAction(Intent.ACTION_GET_CONTENT)
            photoActivityLauncher.launch(intent)
        }

        binding.ivPhotosUpload.setOnClickListener {
            if(PhoneServiceHandler.checkNetworkState(this)){

                presenter.onClickedUploadButton()
            }
            else{
                DialogHandler.showDialogWithResult(this, this, getString(R.string.dialog_settings_network_state), DialogTypeEnums.SettingsNetwork)
            }
        }

        scannerActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                val itemID = it.data?.getStringExtra(AppConfig.SCANNING_RESULT)
                Toast.makeText(this, getString(R.string.photos_scan_id, itemID), Toast.LENGTH_SHORT).show()
                presenter.setItemID(itemID)
            }
        }

        photoActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                result.data?.clipData?.let {clipData ->
                    for(i in 0..<clipData.itemCount){
                        val imageUri = clipData.getItemAt(i).uri

                        imageUri?.let{
                            val path = getRealPathFromURI(it)

                            if(!path.isNullOrEmpty()){
                                presenter.addPhoto(PhotoItem(path, false))
                            }
                        }
                    }

                }
            }
        }

        presenter.setPhotoUploadModel(FileHandler.readFromStorage(this, AppConfig.TEMP_FOLDER, AppConfig.TEMP_PHOTO_UPLOAD_FILE))
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) { // Source is Dropbox or other similar local file path
            return contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }
    }

    override fun showQRCode(bitmap: Bitmap) {
        imageQRCodeView.setImageBitmap(bitmap)
        imageQRCodeView.visibility = View.VISIBLE
        layoutQRCodeEmpty.visibility = View.GONE
    }

    override fun hideQRCode() {
        imageQRCodeView.visibility = View.GONE
        layoutQRCodeEmpty.visibility = View.VISIBLE
    }

    override fun showInformationDialog(information: String, type: DialogTypeEnums) {
        DialogHandler.showInformationDialog(this, information, type)
    }

    override fun showNetworkDialog() {
        startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
    }

    override fun showPhotos(photos: ArrayList<PhotoItem>) {
        photoListAdapter = PhotoListAdapter(this, this, photos)
        photoListView.adapter = photoListAdapter
    }

    override fun showProgressDialog() {
        DialogHandler.showProgressDialog(this, "Fényképek feltöltése...")
    }

    override fun hideProgressDialog() {
        DialogHandler.closeProgressDialog()
    }

    override fun navigateToScannerActivity() {
        scannerActivityLauncher.launch(Intent(this, ScannerActivity::class.java))
    }

    override fun navigateToCameraActivity() {
        imageName = ("${FormatHandler.dateToCustomFormat(Calendar.getInstance().time, AppConfig.DATETIME_FORMAT_yyyyMMdd_HHmmss)}.jpg")

        imageName?.let{

            val imageFile = File(FileHandler.getFolder(this, AppConfig.TEMP_FOLDER, true), it)
            val photoUri = FileProvider.getUriForFile(this, applicationContext?.packageName+".provider", imageFile)

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            startActivityForResult(intent, AppConfig.REQUEST_PHOTO)
        }
    }

    override fun save(photoUploadModel: PhotoUploadModel) {
        FileHandler.saveInStorage(this, AppConfig.TEMP_FOLDER, AppConfig.TEMP_PHOTO_UPLOAD_FILE, photoUploadModel)
    }

    override fun deleteFiles() {
        FileHandler.deleteFolder(this, AppConfig.TEMP_FOLDER)
    }

    override fun closeActivity() {
        finish()
    }

    override fun onDialogResult(result: DialogResultEnums) {
        presenter.onDialogResult(result)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == AppConfig.REQUEST_PHOTO){
            if(resultCode == Activity.RESULT_OK){
                imageName?.let{
                    val image = File(FileHandler.getFolder(this, AppConfig.TEMP_FOLDER), it)
                    presenter.addPhoto(PhotoItem(image.path, true))
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onClickedDeletePhotoButton(path: String?) {
        presenter.deletePhoto(path)
    }

    override fun onClickedPreviewButton(path: String?) {
        path?.let{
            val photoUri = FileProvider.getUriForFile(this, applicationContext.packageName+".provider", File(path))
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.setDataAndType(photoUri, "image/jpg")
            startActivity(intent)
        }
    }
}