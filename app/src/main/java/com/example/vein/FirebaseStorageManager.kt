package com.example.vein

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage

class FirebaseStorageManager {
    
    private val mStorageRef = FirebaseStorage.getInstance().reference
    fun uploadImage(mContext:Context,imageUri: Uri,userId:String){


        val uploadTask = mStorageRef.child("users/$userId.png").putFile(imageUri)
        uploadTask.addOnSuccessListener {

        }.addOnFailureListener{
            Toast.makeText(mContext,"Successful operation ${it.printStackTrace()}",Toast.LENGTH_SHORT).show()

        }
    }
    fun downloadUri(userId:String): Uri? {

        var uri:Uri? = null
        val downloadUriTask=mStorageRef.child("users/$userId.png").downloadUrl
        downloadUriTask.addOnSuccessListener {
            uri=it
        }.addOnFailureListener {
        // text required here
        }
return uri
    }
}