package com.phamtruong.bepngon.ui.user.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.databinding.LayoutBottomSheetPostBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ReactionModel
import com.phamtruong.bepngon.model.SaveModel
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.baidang.DangBaiActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.sever.FirebaseDatabaseUtil
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.ui.chat.RoomChatActivity
import com.phamtruong.bepngon.util.DataUtil
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() , EventClickPostsAdapterListener, SwipeRefreshLayout.OnRefreshListener  {

    lateinit var binding : FragmentHomeBinding
    lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = PostsAdapter(requireContext(), ArrayList<PostModel>(), this)
        binding.rcyBaiDang.adapter = adapter

        initListener()

        initView()

        binding.swipLayout.setOnRefreshListener(this)

        return binding.root
    }

    private fun initView(){
        DataHelper.profileUser.observe(viewLifecycleOwner){
            Picasso.get().load(it.avt).into(binding.imgAvt)
        }
        binding.toolBar.imgChat.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPostData()
    }

    private fun initListener() {
        binding.txtDangBai.setOnSafeClick {
            requireContext().openActivity(DangBaiActivity::class.java)
        }

        binding.imgAvt.setOnClickListener {
            requireContext().openActivity(PersonalPageActivity::class.java)
        }

        binding.toolBar.imgChat.setOnClickListener {
            requireContext().openActivity(RoomChatActivity::class.java)
        }
    }

    override fun clickPost(post : PostModel, position : Int) {
        showBottomSheet(post.accountId == SharePreferenceUtils.getAccountID(), post, position)
    }

    private fun showBottomSheet(boolean: Boolean, post : PostModel, position : Int) {
        val bottomSheetBinding = LayoutBottomSheetPostBinding.inflate(layoutInflater)
        val moreBottomSheet =
            BottomSheetDialog(requireContext())
        moreBottomSheet.setContentView(bottomSheetBinding.root)

        if (boolean) {
            bottomSheetBinding.llDelete.show()
            bottomSheetBinding.llReport.gone()
        } else {
            bottomSheetBinding.llDelete.gone()
            bottomSheetBinding.llReport.show()
        }

        val querySave: Query = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
            .child(FBConstant.SAVE_F)
            .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
        querySave.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (appleSnapshot in dataSnapshot.children) {
                        val reactionModel = appleSnapshot.getValue<ReactionModel>()
                        if (reactionModel?.postId == post.postId) {
                            bottomSheetBinding.llSave.gone()
                            bottomSheetBinding.llSaved.show()
                            return
                        }
                    }
                    bottomSheetBinding.llSave.show()
                    bottomSheetBinding.llSaved.gone()
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                bottomSheetBinding.llSave.show()
                bottomSheetBinding.llSaved.gone()
            }
        })

        bottomSheetBinding.llDelete.setOnClickListener {
            FirebaseDatabase.getInstance().getReference(FirebaseDatabaseUtil.ROOT)
                .child(FBConstant.POST_F).child(post.postId).removeValue().addOnSuccessListener {
                    moreBottomSheet.dismiss()
                    adapter.notifyItemRemoved(position)
                }
        }

        bottomSheetBinding.llSave.setOnClickListener {
            val saveModel = SaveModel(
                DataUtil.ConvertToMD5(DataUtil.getTime()),
                SharePreferenceUtils.getAccountID(),
                post.postId,
                DataUtil.getTime()
            )
            FirebaseDatabase.getInstance().getReference(FirebaseDatabaseUtil.ROOT)
                .child(FBConstant.SAVE_F)
                .child(saveModel.saveId).setValue(saveModel).addOnSuccessListener {
                    moreBottomSheet.dismiss()
                }
        }

        bottomSheetBinding.llSaved.setOnClickListener {
            val query2: Query = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
                .child(FBConstant.SAVE_F)
                .orderByChild("accountId").equalTo(SharePreferenceUtils.getAccountID())
            query2.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (appleSnapshot in dataSnapshot.children) {
                            val reactionModel = appleSnapshot.getValue<ReactionModel>()
                            if (reactionModel?.postId == post.postId) {
                                appleSnapshot.ref.removeValue()
                                moreBottomSheet.dismiss()
                                return
                            }
                        }

                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {}
            })
        }

        moreBottomSheet.show()
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getPostData() {
        mDatabase.child(FBConstant.POST_F).get().addOnSuccessListener {dataSnapshot->
            val listData = ArrayList<PostModel>()
            for (postSnapshot in dataSnapshot.children) {
                postSnapshot.getValue<PostModel>()?.let {
                    listData.add(
                        it
                    )
                }
            }
            adapter.setListData(listData)
            binding.swipLayout.isRefreshing = false
        }.addOnFailureListener {
            requireContext().showToast("Lỗi kết nối!")
        }
    }

    override fun onRefresh() {
        getPostData()
    }

}