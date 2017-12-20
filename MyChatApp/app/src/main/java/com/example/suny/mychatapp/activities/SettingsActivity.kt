package com.example.suny.mychatapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.suny.mychatapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var mDatabaseRef: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null
    var mStorageRef: StorageReference? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mCurrentUser = FirebaseAuth.getInstance().currentUser
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

            }

            override fun onCancelled(dataSnapshotError: DatabaseError?) {

            }
        })

        changeStatusBtnId.setOnClickListener {
            var intent = Intent(this, StatusActivity::class.java)
            intent.putExtra("status", settingStatus.text.toString().trim())
            startActivity(intent)
        }

    }
}
