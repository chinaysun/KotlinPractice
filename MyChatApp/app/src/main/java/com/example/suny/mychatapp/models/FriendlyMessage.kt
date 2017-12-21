package com.example.suny.mychatapp.models

/**
 * Created by suny on 21/12/17.
 */
class FriendlyMessage() {
    var id:String? = null
    var text:String? = null
    var name:String? = null

    constructor(id:String, text:String,name:String): this() {
        this.id = id
        this.text = text
        this.name = name
    }

}