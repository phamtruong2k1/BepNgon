package com.phamtruong.bepngon.ui.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.R
import com.phamtruong.bepngon.databinding.FragmentNotificationBinding
import com.phamtruong.bepngon.databinding.FragmentReportBinding
import com.phamtruong.bepngon.model.NotificationModel
import com.phamtruong.bepngon.model.ReportModel
import com.phamtruong.bepngon.sever.FBConstant
import com.phamtruong.bepngon.ui.adapter.ReportAdapter
import com.phamtruong.bepngon.ui.user.main.noti.NotificationAdapter
import com.phamtruong.bepngon.util.SharePreferenceUtils
import com.phamtruong.bepngon.util.showToast

class ReportFragment : Fragment() {


    lateinit var binding: FragmentReportBinding

    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportBinding.inflate(inflater, container, false)

        val  listDemo = ArrayList<ReportModel>()

        adapter = ReportAdapter(requireContext(), listDemo)
        binding.toolBar.txtTitle.text = "Báo cáo"

        binding.rcyReport.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNotification()
    }

    private val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
    private fun getNotification() {

        mDatabase.child(FBConstant.REPORT_F).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val listData = ArrayList<ReportModel>()
                for (postSnapshot in dataSnapshot.children) {
                    postSnapshot.getValue<ReportModel>()?.let {
                        listData.add(
                            it
                        )
                    }
                }
                listData.sort()
                adapter.setListData(listData)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                requireContext().showToast("Lỗi kết nối!")
            }
        })
    }
}