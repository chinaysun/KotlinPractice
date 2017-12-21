package com.example.suny.mychatapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.suny.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_settings.*
import java.io.ByteArrayOutputStream
import java.io.File

class SettingsActivity : AppCompatActivity() {

    var mDatabaseRef: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null
    var mStorageRef: StorageReference? = null
    var GALLERY_ID: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mCurrentUser = FirebaseAuth.getInstance().currentUser
        mStorageRef = FirebaseStorage.getInstance().reference
        var userId = mCurrentUser!!.uid
        mDatabaseRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

        mDatabaseRef!!.addValueEventListener( object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                var displayName = dataSnapshot!!.child("display_name").value
                var image = dataSnapshot!!.child("image").value
                var userStatus = dataSnapshot!!.child("status").value
                var thumbNail = dataSnapshot!!.child("thumb_image").value

                settingStatus.text = userStatus.toString()
                settingsDisplayNameId.text = displayName.toString()

                if (!image!!.equals("defaults")) {
                    Picasso.with(applicationContext)
                            .load(image.toString())
                            .placeholder(R.drawable.profile_img)
                            .into(settingsProfileId)

                }

            }

            override fun onCancelled(dataSnapshotError: DatabaseError?) {

            }
        })

        changeStatusBtnId.setOnClickListener {
            var intent = Intent(this, StatusActivity::class.java)
            intent.putExtra("status", settingStatus.text.toString().trim())
            startActivity(intent)
        }

        changeProfileBtnId.setOnClickListener {

            var galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent,"SELECT_IMAGE"),GALLERY_ID)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == GALLERY_ID && resultCode == Activity.RESULT_OK) {

            var image: Uri = data!!.data

            CropImage.activity(image)
                    .setAspectRatio(1,1)
                    .start(this)

        }

        // equal value and type
        if (requestCode === CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)

            if (resultCode === Activity.RESULT_OK) {
                val resultUri = result.uri

                var userId = mCurrentUser!!.uid
                var thumbFile = File(resultUri.path)

                var thumbBitmap = Compressor(this)
                        .setMaxWidth(200)
                        .setMaxHeight(200)
                        .setQuality(65)
                        .compressToBitmap(thumbFile)

                //compress image
                var byteArray = ByteArrayOutputStream()
                thumbBitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArray)
                var thumByteArray: ByteArray = byteArray.toByteArray()

                //directory for lager image
                var filePath = mStorageRef!!.child("chat_profile_images").child(userId + ".jpg")

                //Create another directory for thumbImages
                var thumbFilePath = mStorageRef!!.child("chat_profile_images")
                        .child("thumbs")
                        .child(userId + ",jpg")


                filePath.putFile(resultUri)
                        .addOnCompleteListener { 
                            task: Task<UploadTask.TaskSnapshot> ->
                            
                            var downloadUrl = task.result.downloadUrl.toString()
                            
                            var uploadTask: UploadTask = thumbFilePath
                                    .putBytes(thumByteArray)
                            
                            uploadTask.addOnCompleteListener {
                                task: Task<UploadTask.TaskSnapshot> ->

                                    var thumbUrl = task.result.downloadUrl.toString()

                                    if (task.isSuccessful) {

                                        var updateObj = HashMap<String, Any>()
                                        updateObj.put("image", downloadUrl)
                                        updateObj.put("thumb_image",thumbUrl)

                                        //save profile image
                                        mDatabaseRef!!.updateChildren(updateObj)
                                                .addOnCompleteListener {
                                                    task: Task<Void> ->

                                                    if (task.isSuccessful) {
                                                        Toast.makeText(this,"Image Updated Successfully", Toast.LENGTH_LONG)
                                                                .show()
                                                    }
                                                    else {

                                                    }

                                                }

                                    }
                                    else {

                                    }
                            }
                            
                        }



            }
        }
    }

}
