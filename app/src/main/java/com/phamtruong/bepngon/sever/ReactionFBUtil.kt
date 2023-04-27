package com.phamtruong.bepngon.sever

import com.google.firebase.database.FirebaseDatabase
import com.phamtruong.bepngon.model.CommentModel
import com.phamtruong.bepngon.model.ReactionModel

object ReactionFBUtil {

    fun insertComment(reactionModel: ReactionModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.REACTION_F).child(
            reactionModel.reactionId
        ).setValue(reactionModel)
    }

    fun deleteComment(commentModel: CommentModel) {
        val mDatabase = FirebaseDatabase.getInstance().getReference(FBConstant.ROOT)
        mDatabase.child(FBConstant.REACTION_F).child(
            commentModel.commentId
        ).setValue(commentModel)
    }

}