package com.phamtruong.bepngon.ui.personalpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityWithoutPageBinding

class WithoutPageActivity : AppCompatActivity() {

    lateinit var binding: ActivityWithoutPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWithoutPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}