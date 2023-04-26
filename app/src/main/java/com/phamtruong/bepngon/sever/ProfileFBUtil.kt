package com.phamtruong.bepngon.sever

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.phamtruong.bepngon.model.ProfileModel
import com.phamtruong.bepngon.sever.account.AccountFBUtil

object ProfileFBUtil {

    val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)

    fun getProfile(idUser : String, listener : ProfileFBListener) {
        AccountFBUtil.mDatabase.child(FBConstant.PROFILE).child(idUser).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val result = task.result
                val profileModel = result.getValue<ProfileModel>()
                if (profileModel != null) {
                    listener.actionSuccess(profileModel)
                } else {
                    listener.actionFail()
                }
            }
        }.addOnFailureListener {
            listener.actionFail()
        }
    }
}

interface ProfileFBListener {
    fun actionSuccess(profileModel : ProfileModel)
    fun actionFail()
}