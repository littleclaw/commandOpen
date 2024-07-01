package com.stardust.co

import android.app.Application
import cn.jpush.android.api.JPushInterface
import com.drake.brv.utils.BRV

class CoApplication :Application() {
    companion object{
        lateinit var instance: Application
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        JPushInterface.setDebugMode(true)
        JPushInterface.init(this)
//        BRV.modelId = BR.m
    }
}