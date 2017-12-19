package com.example.suny.choreapp.data

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.suny.choreapp.R
import com.example.suny.choreapp.model.Chore
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private val list: ArrayList<Chore>,
                       private val context: Context): RecyclerView.Adapter<ChoreListAdapter.ViewHolder>() {
    override fun getItemCount(): Int { return list.size }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindViews(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)

        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), OnClickListener {

        var choreName = itemView.findViewById<TextView>(R.id.listChoreName)
        var assignBy = itemView.findViewById<TextView>(R.id.listAssignedBy)
        var assignTo = itemView.findViewById<TextView>(R.id.listAssignedTo)
        var assignDate = itemView.findViewById<TextView>(R.id.listDate)
        var deleteButton = itemView.findViewById<Button>(R.id.listDeleteButton)
        var editButton = itemView.findViewById<Button>(R.id.listEditButton)

        fun bindViews(chore: Chore) {
            choreName.text = chore.choreName
            assignBy.text = chore.assignedBy
            assignTo.text = chore.assignedTo
            assignDate.text = chore.showHumanDate(System.currentTimeMillis())

            deleteButton.setOnClickListener(this)
            editButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            var mPosition: Int = adapterPosition
            var clone = list[mPosition]

            when (v!!.id) {
                deleteButton.id -> {
                    deleteChore(clone)
                    list.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editButton.id -> {
                    editChore(clone)
                }
            }

        }

        private fun deleteChore(chore: Chore) {

            var db: ChoresDatabaseHandler = ChoresDatabaseHandler(context)
            db.deleteChore(chore)

        }

        private fun editChore(chore: Chore) {

            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler: ChoresDatabaseHandler = ChoresDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            var choreName = view.popEnterChore
            var assignedBy = view.popAssignBy
            var assignedTo = view.popAssignTo
            var saveButton = view.popSaveChore

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveButton.setOnClickListener {
                var name = choreName.text.toString().trim()
                var aBy = assignedBy.text.toString().trim()
                var aTo = assignedTo.text.toString().trim()

                if (!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(aBy)
                        && !TextUtils.isEmpty(aTo)) {
                    // var chore = Chore()

                    chore.choreName = name
                    chore.assignedTo = aTo
                    chore.assignedBy = aBy

                    dbHandler!!.updateChore(chore)
                    notifyItemChanged(adapterPosition, chore)


                    dialog!!.dismiss()

                } else {

                }
            }
        }


    }

}