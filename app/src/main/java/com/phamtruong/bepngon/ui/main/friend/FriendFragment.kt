package com.phamtruong.bepngon.ui.main.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.databinding.FragmentFriendBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetManageNguoiDungBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetPostBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.model.ReactionModel
import com.phamtruong.bepngon.model.SaveModel
import com.phamtruong.bepngon.ui.adapter.EventClickFriendAdapterListener
import com.phamtruong.bepngon.ui.adapter.FriendAdapter
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.show

class FriendFragment : Fragment(), EventClickFriendAdapterListener {

    private lateinit var binding : FragmentFriendBinding

    private lateinit var adapter: FriendAdapter

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        database = Firebase.database.reference

        binding = FragmentFriendBinding.inflate(inflater, container, false)

        binding.toolBar.txtTitle.text = "Người dùng"

        adapter = FriendAdapter(requireContext(), ArrayList<ProfileModel>(), this)

        binding.rcyFriend.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFriend()
    }

    override fun clickMoreFriend(friend: ProfileModel) {

    }

    override fun clickAvatarFriend(accountID : String) {
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

        mDatabase.child(FBConstant.PROFILE).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<ProfileModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<ProfileModel>()?.let {
                        //if (it.accountId != SharePreferenceUtils.getAccountID())
                        listData.add(
                            it
                        )
                    }
                }
                adapter.setListData(listData)

                showBottomSheet()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                requireContext().showToast("Lỗi kết nối!")
            }
        })
    }

    private fun showBottomSheet() {
        val bottomSheetBinding = LayoutBottomSheetManageNguoiDungBinding.inflate(layoutInflater)
        val moreBottomSheet =
            BottomSheetDialog(requireContext())
        moreBottomSheet.setContentView(bottomSheetBinding.root)


        moreBottomSheet.show()
    }
}