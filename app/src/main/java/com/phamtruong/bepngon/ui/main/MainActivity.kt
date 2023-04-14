package com.phamtruong.bepngon.ui.main

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun initCreate() {
        val adapter = TabViewMainAdapter(this@MainActivity, supportFragmentManager)
        binding.viewPagerMain.adapter = adapter
        binding.btNaviMain.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> binding.viewPagerMain.currentItem = 0
                R.id.menu_da_luu -> binding.viewPagerMain.currentItem = 1
                R.id.menu_friend -> binding.viewPagerMain.currentItem = 2
                R.id.menu_thong_bao -> binding.viewPagerMain.currentItem = 3
                R.id.menu_menu -> binding.viewPagerMain.currentItem = 4
                else -> binding.viewPagerMain.currentItem = 0
            }
            true
        })
    }

    private var isClichBack = false
    override fun onBackPressed() {
        if (isClichBack) {
            finish()
        } else {
            Toast.makeText(this, getString(R.string.click_back), Toast.LENGTH_SHORT).show()
            isClichBack = true
            Handler().postDelayed({
                isClichBack = false
            }, 1000L)
        }
    }

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(inflater)
    }
}