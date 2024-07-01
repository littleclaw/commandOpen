package com.stardust.co.model



class PushMessage {
    var platform = "android"
    var audience: PushAudience? = null
    var notification: PushNotification? = null
    var message: BodyMsg? = null
    var options: PushOption? = null
}