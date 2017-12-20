package com.example.suny.mychatapp.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.suny.mychatapp.R
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var mDatabase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        loginButtonId.setOnClickListener {
            var email = loginEmail.text.toString().trim()
            var password = loginPassword.text.toString().trim()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                loginUser(email, password)
            }else {
                Toast.makeText(this, "Sorry, login failed!", Toast.LENGTH_LONG)
                        .show()
            }

            loginUser(email, password)
        }
    }

    private fun loginUser(email: String, password: String) {

        mAuth!!.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {

                    task: Task<AuthResult> ->

                    if (task.isSuccessful) {

                        var userId = mAuth!!.currentUser!!.uid
                        var databaseRef = FirebaseDatabase.getInstance().reference
                                .child("Users").child(userId).child("display_name")

                        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {

                            override fun onCancelled(error: DatabaseError?) {
                                Toast.makeText(this@LoginActivity, "Sorry, login failed!",
                                        Toast.LENGTH_LONG).show()
                            }

                            override fun onDataChange(snapshot: DataSnapshot?) {

                                var displayName = snapshot!!.getValue(String::class.java)

                                var dashBoardIntent = Intent(this@LoginActivity,
                                        DashboardActivity::class.java)
                                dashBoardIntent.putExtra("name",displayName)
                                startActivity(dashBoardIntent)
                                finish()

                            }
                        })

                    }
                    else {
                        Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG)
                                .show()
                    }
                }


    }
}
