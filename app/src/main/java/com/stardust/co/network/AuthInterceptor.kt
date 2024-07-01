package com.stardust.co.network

import com.blankj.utilcode.util.EncodeUtils
import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

object AuthInterceptor : Interceptor {
    private const val appKey = "5ebe797b43615b9e33a66fee"
    private const val masterSecret = "5ff52f9c0232cf30c28a0df8"
    private const val authString = "$appKey:$masterSecret"

    private var token = "Basic ${EncodeUtils.base64Encode2String(authString.toByteArray())}"
        set(value) {
            LogUtils.d("token set", value)
            field = value
        }

    override fun intercept(chain: Interceptor.Chain): Response {
        val origin = chain.request()
        val builder = origin.newBuilder().addHeader("Authorization", token)
        val response : Response
        try {
            response = chain.proceed(builder.build())

        }catch (e:Exception){
            e.printStackTrace()
            throw IOException(e)
        }
        return response
    }
}