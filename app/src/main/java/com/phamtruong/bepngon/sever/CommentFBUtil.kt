package com.phamtruong.bepngon.sever

import com.google.firebase.database.FirebaseDatabase
import com.phamtruong.bepngon.model.CommentModel
import com.phamtruong.bepngon.model.PostModel

object CommentFBUtil {

    fun insertComment(commentModel: CommentModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.COMMENT_F).child(
            commentModel.commentId
        ).setValue(commentModel)
    }

}