package com.stardust.co

import android.content.Intent
import android.os.Bundle
import com.azhon.appupdate.manager.DownloadManager
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.drake.channel.receiveEvent
import com.pengxh.autodingding.utils.postDelayed
import com.stardust.co.databinding.ActivityMainBinding
import com.stardust.co.util.SendMailUtil
import com.stardust.co.util.SendMailUtil.send
import com.stardust.co.vm.MainVM
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.ext.parseState

class MainActivity : BaseVmDbActivity<MainVM, ActivityMainBinding>() {
    companion object {
        const val EXTRA_ACTION = "action"
        const val ACTION_SEND_MAIL = "sendMail"
        const val ACTION_LAUNCH_DING = "launchDing"
        const val ACTION_SCREENSHOT = "screenShot"
        const val ACTION_MANUAL_SIGN = "manualSign"
        const val TAG_SIGN_SUCCESS = "signSuccess"
    }

    override fun createObserver() {
        receiveEvent<String>(TAG_SIGN_SUCCESS) {
            send(it)
        }
        mViewModel.versionResult.observe(this){resultState->
            parseState(resultState,{version->
                LogUtils.d(GsonUtils.toJson(version))
                val manager = DownloadManager.Builder(this).run {
                    apkUrl(version.apkUrl)
                    apkName("星尘指令.apk")
                    smallIcon(R.mipmap.ic_launcher)
                    //设置了此参数，那么内部会自动判断是否需要显示更新对话框，否则需要自己判断是否需要更新
                    apkVersionCode(version.versionCode)
                    //同时下面三个参数也必须要设置
                    apkVersionName(version.versionName)
                    apkSize(version.apkSize)
                    apkDescription(version.description)
                    build()
                }
                manager.download()
            })
        }
    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.checkVersion()
    }

    override fun showLoading(message: String) {
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        execAction(intent)
    }
    private fun execAction(intent: Intent) {
        val action = intent.getStringExtra(EXTRA_ACTION)
        when(action){
            ACTION_SEND_MAIL->{
                val emailMessage =
                    "如果发送指令1分钟内收到，说明应用正常运行中。" + TimeUtils.getNowString()
                send(emailMessage)
            }
            ACTION_LAUNCH_DING->{
                postDelayed(2000){
                    startActivity(IntentUtils.getLaunchAppIntent(Constants.DING_PACKAGE_NAME))
                }
            }
        }
    }
}