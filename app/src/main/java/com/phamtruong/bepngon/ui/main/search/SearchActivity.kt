package com.phamtruong.bepngon.ui.main.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivitySearchBinding
import com.phamtruong.bepngon.view.show

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.toolBar.txtTitle.text = "Tìm kiếm"
        binding.toolBar.imgBack.show()
        binding.toolBar.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}