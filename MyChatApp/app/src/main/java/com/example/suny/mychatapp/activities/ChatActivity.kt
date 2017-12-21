package com.example.suny.mychatapp.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.TEXT_ALIGNMENT_VIEW_END
import android.widget.TextView
import com.example.suny.mychatapp.R
import com.example.suny.mychatapp.models.FriendlyMessage
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.custom_bar.*
import kotlinx.android.synthetic.main.custom_bar.view.*

class ChatActivity : AppCompatActivity() {
    var userId: String? = null
    var mFirebaseDatabaseRef: DatabaseReference? = null
    var mFirebaseUser: FirebaseUser? = null

    var mLinearLayoutManager: LinearLayoutManager? = null
    var mFirebaseAdapter: FirebaseRecyclerAdapter<FriendlyMessage, MessageViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        mFirebaseUser = FirebaseAuth.getInstance().currentUser
        userId = intent.extras.getString("userId")
        var profileImgLink = intent.extras.get("profile").toString()
        mLinearLayoutManager = LinearLayoutManager(this)
        mLinearLayoutManager!!.stackFromEnd = true

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)

        var inflater = this.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var actionBarView = inflater.inflate(R.layout.custom_bar,null)
        actionBarView.customBarName.text = intent.extras.getString("name")
        Picasso.with(this)
                .load(profileImgLink)
                .placeholder(R.drawable.profile_img)
                .into(actionBarView.customBarCircleImage)

        supportActionBar!!.customView = actionBarView

        mFirebaseDatabaseRef = FirebaseDatabase.getInstance().reference

        mFirebaseAdapter = object : FirebaseRecyclerAdapter<FriendlyMessage,
                MessageViewHolder>(
                FriendlyMessage::class.java,
                R.layout.item_message,
                MessageViewHolder::class.java,
                mFirebaseDatabaseRef!!.child("messages")) {

            override fun populateViewHolder(viewHolder: MessageViewHolder?, model: FriendlyMessage?, position: Int) {

                if (model!!.text != null) {
                    viewHolder!!.bindView(model)

                    var currentUserId = mFirebaseUser!!.uid
                    var isMe: Boolean = model!!.id!!.equals(currentUserId)

                    if (isMe) {
                        // show right side image
                        viewHolder.profileImageViewRight!!.visibility = View.VISIBLE
                        viewHolder.profileImageViewLeft!!.visibility = View.GONE
                        viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)
                        viewHolder.messagerTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.RIGHT)

                        //Get imageUrl for me
                        mFirebaseDatabaseRef!!.child("Users")
                                .child(currentUserId)
                                .addValueEventListener( object: ValueEventListener{
                                    override fun onCancelled(snapshotError: DatabaseError?) {

                                    }

                                    override fun onDataChange(snapshot: DataSnapshot?) {

                                        var imageUrl = snapshot!!.child("thumb_image").value.toString()
                                        var displayName = snapshot!!.child("display_name").value.toString()

                                        viewHolder.messagerTextView!!.text =
                                                "$displayName wrote..."

                                        Picasso.with(viewHolder.profileImageViewRight!!.context)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_img)
                                                .into(viewHolder.profileImageViewRight)

                                    }

                                })

                    }
                    else {

                        viewHolder.profileImageViewRight!!.visibility = View.GONE
                        viewHolder.profileImageViewLeft!!.visibility = View.VISIBLE
                        viewHolder.messageTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)
                        viewHolder.messagerTextView!!.gravity = (Gravity.CENTER_VERTICAL or Gravity.LEFT)

                        //Get imageUrl for me
                        mFirebaseDatabaseRef!!.child("Users")
                                .child(userId)
                                .addValueEventListener( object: ValueEventListener{
                                    override fun onCancelled(snapshotError: DatabaseError?) {

                                    }

                                    override fun onDataChange(snapshot: DataSnapshot?) {

                                        var imageUrl = snapshot!!.child("thumb_image").value.toString()
                                        var displayName = snapshot!!.child("display_name").value.toString()

                                        viewHolder.messagerTextView!!.text = displayName

                                        Picasso.with(viewHolder.profileImageViewRight!!.context)
                                                .load(imageUrl)
                                                .placeholder(R.drawable.profile_img)
                                                .into(viewHolder.profileImageViewLeft)

                                    }

                                })

                    }
                }
            }

        }

        // Set recyclerView
        messageRecyclerView.layoutManager = mLinearLayoutManager
        messageRecyclerView.adapter = mFirebaseAdapter

        sendButton.setOnClickListener {
            if (intent.extras.get("name").toString() != "") {
                var currentUsername = intent.extras.get("name")
                var mCurrentUserId = mFirebaseUser!!.uid

                var friendlyMessage = FriendlyMessage(mCurrentUserId,
                        messageEdt.text.toString().trim(),
                        currentUsername.toString().trim())

                mFirebaseDatabaseRef!!.child("messages")
                        .push().setValue(friendlyMessage)

                messageEdt.setText("")


            }
        }


    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var messageTextView:TextView? = null
        var messagerTextView: TextView? = null
        var profileImageViewLeft: CircleImageView? = null
        var profileImageViewRight: CircleImageView? = null

        fun bindView(message:FriendlyMessage) {

            messageTextView = itemView.findViewById(R.id.messageTextView)
            messagerTextView = itemView.findViewById(R.id.nameOfMessagerTextView)
            profileImageViewLeft = itemView.findViewById(R.id.messageImageViewLeft)
            profileImageViewRight = itemView.findViewById(R.id.messageImageViewRight)

            messageTextView!!.text = message.text
            messagerTextView!!.text = message.name

        }


    }
}
