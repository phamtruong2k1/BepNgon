package com.phamtruong.bepngon.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.phamtruong.bepngon.base.BaseFragment
import com.phamtruong.bepngon.databinding.FragmentHomeBinding
import com.phamtruong.bepngon.model.RecipeModel
import com.phamtruong.bepngon.ui.adapter.EventClickRecipeAdapterListener
import com.phamtruong.bepngon.ui.adapter.RecipeAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding>() , EventClickRecipeAdapterListener {

    lateinit var adapter: RecipeAdapter

    override fun initViewCreated() {
        adapter = RecipeAdapter(requireContext(), ArrayList<RecipeModel>(), this)
        binding.rcyBaiDang.adapter = adapter
    }

    override fun inflateLayout(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun click(idTab: Int) {

    }

}