package com.example.suny.mychatapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.suny.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_status.*

class StatusActivity : AppCompatActivity() {
    var mDatabaseRef: DatabaseReference? = null
    var mCurrentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        supportActionBar!!.title = "Status"

        if (intent.extras != null) {
            var oldStatus = intent.extras.get("status")
            statusUpdateEt.setText(oldStatus.toString())
        }else {
            statusUpdateEt.setText("Enter Your New Status")
        }

        statusUpdateBtn.setOnClickListener {

            mCurrentUser = FirebaseAuth.getInstance().currentUser
            var userId = mCurrentUser!!.uid

            mDatabaseRef = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

            var status = statusUpdateEt.text.toString().trim()

            mDatabaseRef!!.child("status")
                    .setValue(status).addOnCompleteListener {
                        task: Task<Void> ->
                            if (task.isSuccessful) {
                                Toast.makeText(this,"Status Updated Successfully!",Toast.LENGTH_LONG)
                                        .show()
                                startActivity(Intent(this,SettingsActivity::class.java))
                            }
                            else {
                                Toast.makeText(this,"Status Not Updated!",Toast.LENGTH_LONG)
                                        .show()
                            }

                    }



        }
    }
}
