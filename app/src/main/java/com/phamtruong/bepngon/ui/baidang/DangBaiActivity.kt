package com.phamtruong.bepngon.ui.baidang

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.phamtruong.bepngon.databinding.ActivityDangBaiBinding
import com.phamtruong.bepngon.util.Constant.TAG
import java.io.IOException
import java.util.*


class DangBaiActivity : AppCompatActivity() {

    lateinit var binding: ActivityDangBaiBinding

    private var filePath: Uri? = null

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    companion object {
        const val PICK_IMAGE_REQUEST = 12345
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangBaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()
        storageReference = storage?.reference

        initListener()
    }

    private fun initListener() {
        binding.imgBack.setOnClickListener {
            onBack()
        }
        binding.imgAddImage.setOnClickListener {
            chooseImage()
        }
        binding.txtDangBai.setOnClickListener {
            uploadImage()
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                binding.imageView.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun uploadImage() {
        if (filePath != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref = storageReference!!.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {
                    progressDialog.dismiss();
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
                    val downloadUri: Task<Uri> = it.storage.downloadUrl
                    downloadUri.addOnSuccessListener {
                        var imageLink = it.toString()
                        Log.d(TAG, "uploadImage: $imageLink")
                    }.addOnFailureListener {
                        Log.d(TAG, "uploadImage: fail")
                    }
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT)
                        .show()
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }

    override fun onBackPressed() {
        onBack()
    }

    private fun onBack() {
        finish()
    }
}