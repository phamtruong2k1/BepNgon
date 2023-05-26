package com.phamtruong.bepngon.ui.baidang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityDetailBaiDangBinding
import com.phamtruong.bepngon.databinding.ActivityShowAnhBinding
import com.squareup.picasso.Picasso

class ShowAnhActivity : AppCompatActivity() {

    lateinit var binding : ActivityShowAnhBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowAnhBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Picasso.get().load(
            intent.getStringExtra("link")
        ).into(binding.imgShow)

        initListener()
    }

    private fun initListener() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
    }
}