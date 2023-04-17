package com.phamtruong.bepngon.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.databinding.FragmentLoginBinding
import com.phamtruong.bepngon.model.RecipeModel
import com.phamtruong.bepngon.ui.adapter.EventClickRecipeAdapterListener
import com.phamtruong.bepngon.ui.adapter.RecipeAdapter
import com.phamtruong.bepngon.ui.baidang.DangBaiActivity
import com.phamtruong.bepngon.ui.chat.ChatActivity
import com.phamtruong.bepngon.ui.personalpage.PersonalPageActivity
import com.phamtruong.bepngon.util.DataHelper
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

        adapter = RecipeAdapter(requireContext(), ArrayList<RecipeModel>(), this)
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

}