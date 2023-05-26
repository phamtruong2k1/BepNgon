package com.phamtruong.bepngon.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentManageAccountBinding
import com.phamtruong.bepngon.databinding.FragmentMenuAdminBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.EventClickFriendAdapterListener
import com.phamtruong.bepngon.ui.adapter.FriendAdapter
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity


class ManageAccountFragment : BaseFragment<FragmentManageAccountBinding>(),
    EventClickFriendAdapterListener {

    private lateinit var adapter: FriendAdapter

    private lateinit var database: DatabaseReference

    override fun initViewCreated() {
        binding.toolBar.txtTitle.text = "Người dùng"

        adapter = FriendAdapter(requireContext(), ArrayList<String>(), this)

        binding.rcyFriend.adapter = adapter

        getFriend()
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentManageAccountBinding {
        return FragmentManageAccountBinding.inflate(inflater)
    }

    override fun clickMoreFriend(accountID: String) {

    }

    override fun clickAvatarFriend(accountID: String) {
        if (accountID == SharePreferenceUtils.getAccountID()) {
            requireContext().openActivity(
                PersonalPageActivity::class.java
            )
        } else {
            requireContext().openActivity(
                WithoutPageActivity::class.java,
                bundleOf("idUser" to accountID)
            )
        }
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getFriend() {

        mDatabase.child(FBConstant.ACCOUNT).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<String>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<AccountModel>()?.let {
                        if (it.account_id != SharePreferenceUtils.getAccountID())
                            listData.add(
                                it.account_id
                            )
                    }
                }
                adapter.setListData(listData)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                requireContext().showToast("Lỗi kết nối!")
            }
        })
    }


}