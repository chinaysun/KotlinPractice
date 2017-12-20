package com.example.suny.mychatapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.suny.mychatapp.activities.CreateAccountActivity
import com.example.suny.mychatapp.activities.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createAccountButton.setOnClickListener {

            startActivity(Intent(this, CreateAccountActivity::class.java))

        }

        loginButton.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
}