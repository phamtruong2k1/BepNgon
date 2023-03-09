package com.phamtruong.bepngon.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivityMainBinding

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setTag("Note"))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setTag("Home"))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setTag("Theo dõi"))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setTag("Gợi ý"))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setTag("Chat"))
        binding.tabLayoutMain.addTab(binding.tabLayoutMain.newTab().setTag("Profie"))
        val adapter = TabViewMainAdapter(this@MainActivity, supportFragmentManager)
        binding.viewPagerMain.adapter = adapter
        binding.viewPagerMain.currentItem = 0

        binding.tabLayoutMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.viewPagerMain.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(viewModel: MainViewModel) {}
}