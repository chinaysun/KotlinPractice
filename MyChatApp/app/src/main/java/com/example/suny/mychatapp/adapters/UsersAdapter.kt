package com.example.suny.mychatapp.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.suny.mychatapp.R
import com.example.suny.mychatapp.activities.ChatActivity
import com.example.suny.mychatapp.activities.ProfileActivity
import com.example.suny.mychatapp.models.Users
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

/**
 * Created by suny on 21/12/17.
 */
class UsersAdapter(databaseQuery: DatabaseReference, var context: Context)
    :FirebaseRecyclerAdapter<Users, UsersAdapter.ViewHolder>(
        Users::class.java,
        R.layout.users_row,
        UsersAdapter.ViewHolder::class.java,
        databaseQuery

){

    override fun populateViewHolder(viewHolder: UsersAdapter.ViewHolder?, user: Users?, position: Int) {

        var userId = getRef(position).key
        viewHolder!!.bindView(user!!, context)

        viewHolder!!.itemView.setOnClickListener {

            var options = arrayOf("Open Profile","Send Message")
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Select Options")
            builder.setItems(options, DialogInterface.OnClickListener { dialog, i ->
                var userName = viewHolder.userNameText
                var userStatus = viewHolder.userStatusTxt
                var profilePic = viewHolder.userProfilePicLink

                if ( i == 0 ) {
                    var profileIntent = Intent(context, ProfileActivity::class.java)
                    profileIntent.putExtra("userId", userId)
                    context.startActivity(profileIntent)

                } else {
                    var chatIntent = Intent(context, ChatActivity::class.java)
                    chatIntent.putExtra("userId", userId)
                    chatIntent.putExtra("name",userName)
                    chatIntent.putExtra("status",userStatus)
                    chatIntent.putExtra("profile",profilePic)
                    context.startActivity(chatIntent)
                }
            })

            builder.show()



        }

    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var userNameText: String? = null
        var userStatusTxt: String? = null
        var userProfilePicLink: String? = null

        fun bindView(user:Users, context: Context) {
            var userName = itemView.findViewById<TextView>(R.id.userChatDisplayName)
            var userStatus = itemView.findViewById<TextView>(R.id.userChatStatus)
            var userProfilePic = itemView.findViewById<CircleImageView>(R.id.usersChatProfile)

            userNameText = user.display_name
            userStatusTxt = user.status
            userProfilePicLink = user.thumb_image

            userName.text = user.display_name
            userStatus.text = user.status

            Picasso.with(context)
                    .load(userProfilePicLink)
                    .placeholder(R.drawable.profile_img)
                    .into(userProfilePic)

        }

    }



}