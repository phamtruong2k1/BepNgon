package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityDangBaiBinding
import com.phamtruong.bepngon.databinding.ActivityPersonalPageBinding

class PersonalPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityPersonalPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}