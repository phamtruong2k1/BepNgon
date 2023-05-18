package com.phamtruong.bepngon.ui.tienich.chedoan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.ActivityCheDoAnBinding
import com.phamtruong.bepngon.databinding.ActivityChiSoBmiBinding

class CheDoAnActivity : AppCompatActivity() {
    lateinit var binding: ActivityCheDoAnBinding

    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheDoAnBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerFragment) as NavHostFragment
        navController = navHostFragment!!.navController
    }
}