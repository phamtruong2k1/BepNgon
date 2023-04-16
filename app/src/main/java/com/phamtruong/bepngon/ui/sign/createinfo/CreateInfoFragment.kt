package com.phamtruong.bepngon.ui.sign.createinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.FirebaseDatabase
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentCreateInfoBinding
import com.phamtruong.bepngon.databinding.FragmentLogUpBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.account.AccountFirebaseUtil
import com.phamtruong.bepngon.ui.main.MainActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.squareup.picasso.Picasso

class CreateInfoFragment : Fragment() {

    lateinit var binding: FragmentCreateInfoBinding

    companion object {
        const val ROOT = "root"
        const val PROFILE = "profile"
    }

    lateinit var profile: ProfileModel

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
        binding.toolBar.imgSearch.gone()
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

    }

    private fun addNewProfile(profileModel: ProfileModel) {
        AccountFirebaseUtil.mDatabase.child(PROFILE).child(SharePreferenceUtils.getAccountID())
            .setValue(profileModel)
        requireContext().openActivity(MainActivity::class.java, true)
    }


}