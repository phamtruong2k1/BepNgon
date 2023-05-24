package com.phamtruong.bepngon.ui.admin

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.base.BaseActivity
import com.phamtruong.bepngon.databinding.ActivityMainAdminBinding
import com.phamtruong.bepngon.databinding.ActivityMainBinding
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.main.TabViewMainAdapter
import com.phamtruong.bepngon.ui.main.search.SearchActivity
import com.phamtruong.bepngon.util.DataHelper
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast
import com.phamtruong.bepngon.view.gone
import com.phamtruong.bepngon.view.openActivity
import com.phamtruong.bepngon.view.show

class MainAdminActivity : BaseActivity<ActivityMainAdminBinding>() {

    override fun onResume() {
        super.onResume()
        binding.viewPagerMain.currentItem = numberMenu
        binding.btNaviMain.selectedItemId = when (numberMenu) {
            0 -> {
                R.id.menu_home
            }
            2 -> {
                R.id.menu_friend
            }
            3 -> {
                R.id.menu_thong_bao
            }
            4 -> {
                R.id.menu_menu
            }
            else -> {
                R.id.menu_home
            }
        }

        getDataProfileUser()
    }

    private var numberMenu = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun initCreate() {
        val adapter = TabViewMainAdapter(this@MainAdminActivity, supportFragmentManager)
        binding.viewPagerMain.adapter = adapter
        binding.btNaviMain.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    numberMenu = 0
                    binding.viewPagerMain.currentItem = 0
                }
                R.id.menu_search -> {
                    openActivity(SearchActivity::class.java)
                }
                R.id.menu_friend -> {
                    numberMenu = 2
                    binding.viewPagerMain.currentItem = 2
                }
                R.id.menu_thong_bao -> {
                    numberMenu = 3
                    binding.viewPagerMain.currentItem = 3
                }
                R.id.menu_menu -> {
                    numberMenu = 4
                    binding.viewPagerMain.currentItem = 4
                }
                else -> {
                    numberMenu = 0
                    binding.viewPagerMain.currentItem = 0
                }
            }
            true
        })
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getDataProfileUser() {
        val id = SharePreferenceUtils.getAccountID()
        mDatabase.child(FBConstant.PROFILE).child(id).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val profileModel = result.getValue<ProfileModel>()
                if (profileModel != null) {
                    if (profileModel != DataHelper.profileUser.value )
                        DataHelper.profileUser.postValue(profileModel)
                } else {
                    showToast("Có lỗi kết nối!")
                }
            }
        }.addOnFailureListener {
            showToast("Có lỗi kết nối!")
        }
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

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityMainAdminBinding {
        return ActivityMainAdminBinding.inflate(inflater)
    }
}