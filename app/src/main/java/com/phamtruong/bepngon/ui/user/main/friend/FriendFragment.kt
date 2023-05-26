package com.phamtruong.bepngon.ui.user.main.friend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.phamtruong.bepngon.databinding.FragmentFriendBinding
import com.phamtruong.bepngon.model.FollowModel
import com.phamtruong.bepngon.ui.adapter.EventClickFriendAdapterListener
import com.phamtruong.bepngon.ui.adapter.FriendAdapter
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.SearchUserActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.ui.personalpage.WithoutPageActivity
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.show

class FriendFragment : Fragment(), EventClickFriendAdapterListener, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding : FragmentFriendBinding

    private lateinit var adapter: FriendAdapter

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        database = Firebase.database.reference

        binding = FragmentFriendBinding.inflate(inflater, container, false)

        binding.toolBar.txtTitle.text = "Bạn bè"

        binding.toolBar.imgSearch.show()

        adapter = FriendAdapter(requireContext(), ArrayList<String>(), this)

        binding.rcyFriend.adapter = adapter

        binding.swipLayout.setOnRefreshListener(this)

        binding.toolBar.imgSearch.setOnClickListener {
            requireContext().openActivity(
                SearchUserActivity::class.java,
                bundleOf("action" to "friend")
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFriend()
    }

    override fun clickMoreFriend(accountID: String) {

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

        mDatabase.child(FBConstant.FOLLOW_F)
            .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<String>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<FollowModel>()?.let {
                        listData.add(it.account_follow_id)
                    }
                }
                adapter.setListData(listData)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                requireContext().showToast("Lỗi kết nối!")
            }
        })
    }



    override fun onRefresh() {
        binding.swipLayout.isRefreshing = false
    }

    /*private fun showBottomSheet() {
        val bottomSheetBinding = LayoutBottomSheetManageNguoiDungBinding.inflate(layoutInflater)
        val moreBottomSheet =
            BottomSheetDialog(requireContext())
        moreBottomSheet.setContentView(bottomSheetBinding.root)


        moreBottomSheet.show()
    }*/
}