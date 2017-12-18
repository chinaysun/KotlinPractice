package com.example.suny.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.Toast
import com.example.suny.choreapp.R
import com.example.suny.choreapp.data.ChoreListAdapter
import com.example.suny.choreapp.data.ChoresDatabaseHandler
import com.example.suny.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = ChoresDatabaseHandler(this)


        saveChore.setOnClickListener {

            if (!TextUtils.isEmpty(enterChoreId.text.toString())
                    && !TextUtils.isEmpty(assignById.text.toString())
                    && !TextUtils.isEmpty(assignToId.text.toString())) {

                // saved to db
                var chore: Chore = Chore()
                chore.choreName = enterChoreId.text.toString()
                chore.assignedTo = assignToId.text.toString()
                chore.assignedBy = assignById.text.toString()

                saveToDB(chore)

                startActivity(Intent(this,ChoreListActivity::class.java))

            }
            else {
                Toast.makeText(this,"Please enter a chore",Toast.LENGTH_LONG).show()
            }


        }
    }

    fun saveToDB(chore: Chore) {

        dbHandler!!.createChroe(chore)

    }

}
