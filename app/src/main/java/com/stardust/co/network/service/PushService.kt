package com.stardust.co.network.service

import com.stardust.co.model.PushMessage
import com.stardust.co.model.PushResp
import retrofit2.http.Body
import retrofit2.http.POST

interface PushService {
    companion object{
        const val PUSH_SERVER_URL = "https://api.jpush.cn/"
    }
    @POST("v3/push")
    suspend fun pushMsg(@Body pushMessage: PushMessage): PushResp
}