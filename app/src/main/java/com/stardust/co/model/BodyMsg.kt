package com.stardust.co.model

import org.json.JSONObject

class BodyMsg {
    var msg_content:String = ""
    var title = "cmd"
    var content_type:String? = null
    var extras:JSONObject? = null
}