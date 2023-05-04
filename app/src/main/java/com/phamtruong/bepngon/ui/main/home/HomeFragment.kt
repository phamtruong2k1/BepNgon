package com.phamtruong.bepngon.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.model.AccountModel
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.ui.adapter.EventClickPostsAdapterListener
import com.phamtruong.bepngon.ui.baidang.DangBaiActivity
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.PostsAdapter
import com.phamtruong.bepngon.ui.chat.RoomChatActivity
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso

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
        //binding.toolBar.imgChat.show()
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

    override fun clickPost(post : PostModel) {

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