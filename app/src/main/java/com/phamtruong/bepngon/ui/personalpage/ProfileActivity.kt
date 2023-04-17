package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityProfileBinding
import com.phamtruong.bepngon.databinding.ActivityWithoutPageBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}