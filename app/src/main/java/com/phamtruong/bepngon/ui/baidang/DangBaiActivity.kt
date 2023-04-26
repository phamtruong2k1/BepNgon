package com.phamtruong.bepngon.ui.baidang

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.phamtruong.bepngon.databinding.ActivityDangBaiBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.ui.adapter.EventClickImageAdapterListener
import com.phamtruong.bepngon.ui.adapter.ImagePostAdapter
import com.phamtruong.bepngon.util.Constant.TAG
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.hide
import com.phamtruong.bepngon.view.show
import java.io.IOException
import java.util.*


class DangBaiActivity : AppCompatActivity() {

    lateinit var binding: ActivityDangBaiBinding

    lateinit var adapter: ImagePostAdapter

    private var filePath: Uri? = null

    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    var listImage = ArrayList<Uri>()

    companion object {
        const val PICK_IMAGE_REQUEST = 12345
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDangBaiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ImagePostAdapter(this, listImage, object : EventClickImageAdapterListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun click(pos: Int) {
                listImage.removeAt(pos)
                if (listImage.size == 0) {
                    binding.llImage.gone()
                    binding.imgAddImage.show()
                }
                adapter.notifyDataSetChanged()
            }
        })

        binding.rcyImage.adapter = adapter

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

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                filePath?.let {
                    listImage.add(it)
                    adapter.notifyDataSetChanged()
                    binding.llImage.show()
                    binding.imgAddImage.hide()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private var listLinkImage = ArrayList<String>()
    private fun uploadImage() {
        val time = DataUtil.getTime()
        val postId = FirebaseDatabaseUtil.ConvertToMD5(time)
        if (listImage.size == 0) {
            upPost(
                PostModel(
                    postId,
                    SharePreferenceUtils.getAccountID(),
                    "",
                    binding.edtContent.text.toString(),
                    "",
                    time
                )
            )
        } else {
            listImage.let { dataImage ->
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Đăng bài...")
                progressDialog.show()
                dataImage.forEachIndexed { _, image ->
                    val ref = storageReference!!.child("images/" + UUID.randomUUID().toString())
                    ref.putFile(image)
                        .addOnSuccessListener {
                            progressDialog.dismiss();
                            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show()
                            val downloadUri: Task<Uri> = it.storage.downloadUrl
                            downloadUri.addOnSuccessListener { link ->
                                val imageLink = link.toString()
                                Log.d(TAG, "uploadImage: $imageLink")
                                upPost(
                                    PostModel(
                                        postId,
                                        SharePreferenceUtils.getAccountID(),
                                        "",
                                        binding.edtContent.text.toString(),
                                        imageLink,
                                        time
                                    )
                                )
                            }.addOnFailureListener {
                                Toast.makeText(this, "Có lỗi!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            progressDialog.dismiss()
                            Toast.makeText(this, "Có lỗi!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnProgressListener { taskSnapshot ->
                            val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                            progressDialog.setMessage("Đang tải " + progress.toInt() + "%")
                        }
                }

            }
        }
    }

    private fun upPost(post : PostModel) {
        FirebaseDatabaseUtil.mDatabase.child(FBConstant.POST_F).child(
            post.postId
        ).setValue(post).addOnCompleteListener{
            finish()
            showToast("Đăng thành công.")
        }.addOnFailureListener {
            showToast("Đăng thất bại.")
        }
    }

    override fun onBackPressed() {
        onBack()
    }

    private fun onBack() {
        finish()
    }
}