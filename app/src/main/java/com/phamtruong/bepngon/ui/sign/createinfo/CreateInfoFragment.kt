package com.phamtruong.bepngon.ui.sign.createinfo

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentCreateInfoBinding
import com.phamtruong.bepngon.databinding.FragmentLogUpBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetMoreBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.account.AccountFirebaseUtil
import com.phamtruong.bepngon.ui.baidang.DangBaiActivity
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso
import java.io.IOException
import java.util.*

class CreateInfoFragment : Fragment() {

    lateinit var binding: FragmentCreateInfoBinding

    companion object {
        const val ROOT = "root"
        const val PROFILE = "profile"
        const val PICK_IMAGE_REQUEST = 12345
    }

    var chooseNamorNu = true
    var ngayThangNam = ""
    var namSinh = 2023

    lateinit var profile: ProfileModel

    private var filePath: Uri? = null


    val mDatabase = FirebaseDatabase.getInstance().getReference(ROOT)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        profile = ProfileModel(
            SharePreferenceUtils.getAccountID(),
            SharePreferenceUtils.getAccountID(),
            SharePreferenceUtils.getUserName(),
            "",
            true,
            Constant.URL_AVATAR_DEFAUT,
            "",
            ""
        )

        binding = FragmentCreateInfoBinding.inflate(inflater, container, false)

        binding.toolBar.imgChat.gone()
        binding.toolBar.txtTitle.text = "Thông tin người dùng"

        binding.edtName.setText(profile.name)
        binding.edtBirthDay.setText(profile.birthDay)

        Picasso.get().load(profile.avt).into(binding.imgAvt)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtNext.setOnClickListener {
            addNewProfile(profile)
        }

        binding.txtSkip.setOnClickListener {
            addNewProfile(
                ProfileModel(
                    SharePreferenceUtils.getAccountID(),
                    SharePreferenceUtils.getAccountID(),
                    SharePreferenceUtils.getUserName(),
                    "",
                    true,
                    Constant.URL_AVATAR_DEFAUT,
                    "",
                    ""
                )
            )
        }

        binding.edtBirthDay.setOnSafeClick {
            showBottomSheet()
        }

        binding.imgAvt.setOnSafeClick {
            chooseImage()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DangBaiActivity.PICK_IMAGE_REQUEST && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
            try {
                binding.imgAvt.setImageBitmap(MediaStore.Images.Media.getBitmap(requireContext().contentResolver, filePath))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun chooseImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            DangBaiActivity.PICK_IMAGE_REQUEST
        )
    }

    private fun uploadImage() {
        /*if (listImage.size == 0) {
            upPost()
        } else {
            listImage.let { dataImage ->
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()
                dataImage.forEachIndexed { index, image ->
                    val ref = storageReference!!.child("images/" + UUID.randomUUID().toString())
                    ref.putFile(image)
                        .addOnSuccessListener {
                            progressDialog.dismiss();
                            Toast.makeText(this, "Uploaded", Toast.LENGTH_SHORT).show();
                            val downloadUri: Task<Uri> = it.storage.downloadUrl
                            downloadUri.addOnSuccessListener { link ->
                                val imageLink = link.toString()
                                Log.d(Constant.TAG, "uploadImage: $imageLink")
                            }.addOnFailureListener {
                                Log.d(Constant.TAG, "uploadImage: fail")
                            }
                        }
                        .addOnFailureListener { e ->
                            progressDialog.dismiss()
                            Toast.makeText(this, "Failed " + e.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                        .addOnProgressListener { taskSnapshot ->
                            val progress =(index *(100.0/dataImage.size)) + 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount / dataImage.size
                            progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                        }
                }
                upPost()
            }
        }*/
    }

    private fun addNewProfile(profileModel: ProfileModel) {
        AccountFirebaseUtil.mDatabase.child(PROFILE).child(SharePreferenceUtils.getAccountID())
            .setValue(profileModel)
        requireContext().openActivity(MainActivity::class.java, true)
    }

    private val myCalendar = Calendar.getInstance()
    private fun showBottomSheet() {
        val bottomSheetBinding = LayoutBottomSheetMoreBinding.inflate(layoutInflater)
        val moreBottomSheet =
            BottomSheetDialog(requireContext())
        moreBottomSheet.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.namSinh.maxValue = 2023
        bottomSheetBinding.namSinh.minValue = 1950
        bottomSheetBinding.namSinh.value = myCalendar[Calendar.YEAR]
        bottomSheetBinding.namSinh.wrapSelectorWheel = false

        bottomSheetBinding.ngaySinh.maxValue = 31
        bottomSheetBinding.ngaySinh.minValue = 1
        bottomSheetBinding.ngaySinh.value = myCalendar[Calendar.DAY_OF_MONTH]
        bottomSheetBinding.ngaySinh.wrapSelectorWheel = false

        bottomSheetBinding.thangSinh.maxValue = 12
        bottomSheetBinding.thangSinh.minValue = 1
        bottomSheetBinding.thangSinh.value = myCalendar[Calendar.MONTH] + 1
        bottomSheetBinding.thangSinh.wrapSelectorWheel = false

        var isNhuan = false

        bottomSheetBinding.namSinh.setOnValueChangedListener { picker, oldVal, newVal ->
            val values = bottomSheetBinding.namSinh.value
            isNhuan = if (values % 100 == 0) {
                values % 400 == 0
            } else {
                values % 4 == 0
            }
            if (isNhuan) {
                if (bottomSheetBinding.thangSinh.value == 2) {
                    bottomSheetBinding.ngaySinh.maxValue = 29
                }
            } else {
                if (bottomSheetBinding.thangSinh.value == 2) {
                    bottomSheetBinding.ngaySinh.maxValue = 28
                }
            }
        }

        bottomSheetBinding.thangSinh.setOnValueChangedListener { picker, oldVal, newVal ->
            when (bottomSheetBinding.thangSinh.value) {
                1, 3, 5, 7, 8, 10, 12 -> {
                    bottomSheetBinding.ngaySinh.maxValue = 31
                }
                4, 6, 9, 11 -> {
                    bottomSheetBinding.ngaySinh.maxValue = 30
                }
                else -> {
                    if (isNhuan) {
                        bottomSheetBinding.ngaySinh.maxValue = 29
                    } else {
                        bottomSheetBinding.ngaySinh.maxValue = 28
                    }
                }
            }
        }

        moreBottomSheet.setOnDismissListener {
            ngayThangNam = "${bottomSheetBinding.ngaySinh.value}/${bottomSheetBinding.thangSinh.value}/${bottomSheetBinding.namSinh.value}"
            namSinh = bottomSheetBinding.namSinh.value
            binding.edtBirthDay.text = ngayThangNam
            //binding.edtBirthDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        }

        moreBottomSheet.show()
    }


}