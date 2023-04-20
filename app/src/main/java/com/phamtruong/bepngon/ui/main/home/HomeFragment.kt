package com.phamtruong.bepngon.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.model.PostModel
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.ui.adapter.EventClickRecipeAdapterListener
import com.phamtruong.bepngon.ui.adapter.RecipeAdapter
import com.phamtruong.bepngon.ui.baidang.DangBaiActivity
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.FBConstant
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.setOnSafeClick
import com.phamtruong.bepngon.view.show
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() , EventClickRecipeAdapterListener {

    lateinit var binding : FragmentHomeBinding
    lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = RecipeAdapter(requireContext(), ArrayList<PostModel>(), this)
        binding.rcyBaiDang.adapter = adapter

        initListener()

        initView()

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
        getPost()
    }

    private fun initListener() {
        binding.txtDangBai.setOnSafeClick {
            requireContext().openActivity(DangBaiActivity::class.java)
        }

        binding.imgAvt.setOnClickListener {
            requireContext().openActivity(PersonalPageActivity::class.java)
        }

        binding.toolBar.imgChat.setOnClickListener {
            requireContext().openActivity(ChatActivity::class.java)
        }
    }

    override fun click(idTab: Int) {

    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getPost() {

        mDatabase.child(FBConstant.POST_F).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<PostModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<PostModel>()?.let {
                        listData.add(
                            it
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