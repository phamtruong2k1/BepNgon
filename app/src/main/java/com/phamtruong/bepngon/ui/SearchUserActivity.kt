package com.phamtruong.bepngon.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivitySearchBinding
import com.phamtruong.bepngon.databinding.ActivitySearchUserBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetManageNguoiDungBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.EventClickFriendAdapterListener
import com.phamtruong.bepngon.ui.adapter.FriendAdapter
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.Constant
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.show

class SearchUserActivity : AppCompatActivity() , EventClickFriendAdapterListener {

    private lateinit var binding : ActivitySearchUserBinding

    lateinit var adapter: FriendAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FriendAdapter(this, ArrayList<String>(), this)
        binding.rcyKetQua.adapter = adapter

        initView()

        initSearch()
    }

    private fun initSearch() {
        binding.edtSearch.doOnTextChanged { text, start, before, count ->
            if (text.toString().length > 2) {
                searchUser(text.toString())
            } else {
                adapter.setListData(ArrayList())
            }
        }
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun searchUser(data: String) {
        mDatabase.child(FBConstant.PROFILE).get().addOnSuccessListener { dataSnapshot->
            val listData = ArrayList<String>()
            for (postSnapshot in dataSnapshot.children) {
                postSnapshot.getValue<ProfileModel>()?.let {
                    if (
                        it.accountId != SharePreferenceUtils.getAccountID()
                        && DataUtil.checkSearch(it.name, data)
                    )
                        listData.add(
                            it.accountId
                        )
                }
            }
            adapter.setListData(listData)
        }.addOnFailureListener {

        }
    }

    override fun clickMoreFriend(accountID: String) {
        if (!SharePreferenceUtils.isAdmin()) {
            return
        }
        FirebaseDatabase.getInstance().getReference(FBConstant.ROOT).child(FBConstant.ACCOUNT).child(accountID).get().addOnCompleteListener{ task->
            if (task.isSuccessful) {
                val result = task.result
                val accountModel = result.getValue<AccountModel>()
                accountModel?.let {
                    showBottomSheet(it)
                }
            }

        }.addOnFailureListener {

        }
    }

    private fun showBottomSheet(accountModel: AccountModel) {
        val bottomSheetBinding = LayoutBottomSheetManageNguoiDungBinding.inflate(layoutInflater)
        val moreBottomSheet =
            BottomSheetDialog(this)
        moreBottomSheet.setContentView(bottomSheetBinding.root)

        if (accountModel.status) {
            bottomSheetBinding.llMKhoa.show()
            bottomSheetBinding.llKhoa.gone()
        } else {
            bottomSheetBinding.llMKhoa.gone()
            bottomSheetBinding.llKhoa.show()
        }


        bottomSheetBinding.llMKhoa.setOnClickListener {
            accountModel.status = false
            FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
                .child(FBConstant.ACCOUNT).child(accountModel.account_id)
                .setValue(accountModel)
            moreBottomSheet.dismiss()
        }

        bottomSheetBinding.llKhoa.setOnClickListener {
            accountModel.status = true
            FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
                .child(FBConstant.ACCOUNT).child(accountModel.account_id)
                .setValue(accountModel)
            moreBottomSheet.dismiss()
        }


        moreBottomSheet.show()
    }

    override fun clickAvatarFriend(accountID: String) {
        if (accountID == Constant.ID_ADMIN) {
            return
        }
        if (intent.getStringExtra("action").toString().equals("chat")) {
            openActivity(
                ChatActivity::class.java,
                bundleOf(
                    "idUser" to SharePreferenceUtils.getAccountID(),
                    "idYour" to accountID,
                )
            )
        } else {
            openActivity(
                WithoutPageActivity::class.java,
                bundleOf("idUser" to accountID)
            )
        }

    }

    private fun initView() {
        binding.toolBar.txtTitle.text = "Tìm kiếm"
        binding.toolBar.imgBack.show()
        binding.toolBar.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}