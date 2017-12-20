package com.example.suny.mychatapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.suny.mychatapp.R
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
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

        }
    }
}
