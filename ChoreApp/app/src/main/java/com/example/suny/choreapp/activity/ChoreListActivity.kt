package com.example.suny.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.suny.choreapp.R
import com.example.suny.choreapp.data.ChoreListAdapter
import com.example.suny.choreapp.data.ChoresDatabaseHandler
import com.example.suny.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {

    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<Chore>? = null
    private var choreListItems: ArrayList<Chore>? = null
    private var dialogBuilder: AlertDialog.Builder? = null
    private var dialog: AlertDialog? = null

    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: ChoresDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        dbHandler = ChoresDatabaseHandler(this)

        choreList = ArrayList<Chore>()
        choreListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreListItems!!, this)


        recyclerViewId.layoutManager = layoutManager
        recyclerViewId.adapter = adapter

        choreList = dbHandler!!.readChores()
        choreList!!.reverse()

        for (c in choreList!!.iterator()) {
            val chore = Chore()
            chore.choreName = "Chore ${c.choreName}"
            chore.assignedBy = "Assigned By: ${c.assignedBy}"
            chore.assignedTo = "Assigned To: ${c.assignedTo}"
            chore.showHumanDate(c.timeAssigned!!)

            choreListItems!!.add(chore)
        }

        adapter!!.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.add_menu_button) {
            createPopupDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {
        var view = layoutInflater.inflate(R.layout.popup, null)
        var choreName = view.popEnterChore
        var assignedBy = view.popAssignBy
        var assignedTo = view.popAssignTo
        var saveButton = view.popSaveChore

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {

            if (!TextUtils.isEmpty(choreName.text.toString())
                    && !TextUtils.isEmpty(assignedBy.text.toString())
                    && !TextUtils.isEmpty(assignedTo.text.toString())) {

                // saved to db
                var chore: Chore = Chore()
                chore.choreName = choreName.text.toString()
                chore.assignedTo = assignedTo.text.toString()
                chore.assignedBy = assignedBy.text.toString()

                dbHandler?.createChroe(chore)
                dialog!!.dismiss()

                startActivity(Intent(this,ChoreListActivity::class.java))
                finish()

            }
            else {
                Toast.makeText(this,"Please enter a chore", Toast.LENGTH_LONG).show()
            }

        }
    }

}
