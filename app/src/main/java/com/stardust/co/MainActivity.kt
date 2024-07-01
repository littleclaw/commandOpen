package com.stardust.co

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.azhon.appupdate.manager.DownloadManager
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.TimeUtils
import com.drake.channel.receiveEvent
import com.pengxh.autodingding.utils.postDelayed
import com.stardust.co.databinding.ActivityMainBinding
import com.stardust.co.service.NotificationMonitorService
import com.stardust.co.util.SendMailUtil.send
import com.stardust.co.vm.MainVM
import me.hgj.jetpackmvvm.base.activity.BaseVmDbActivity
import me.hgj.jetpackmvvm.ext.parseState
import me.hgj.jetpackmvvm.ext.util.notificationManager

class MainActivity : BaseVmDbActivity<MainVM, ActivityMainBinding>() {
    companion object {
        const val EXTRA_ACTION = "action"
        const val ACTION_SEND_MAIL = "sendMail"
        const val ACTION_LAUNCH_DING = "launchDing"
        const val ACTION_SCREENSHOT = "screenShot"
        const val ACTION_MANUAL_SIGN = "manualSign"
        const val TAG_SIGN_SUCCESS = "signSuccess"
    }

    private val isNotificationEnable: Boolean
        get() {
            val packageNames =
                NotificationManagerCompat.getEnabledListenerPackages(this)
            return packageNames.contains(this.packageName)
        }

    override fun createObserver() {
        receiveEvent<String>(TAG_SIGN_SUCCESS) {
            send(it)
        }
        mViewModel.versionResult.observe(this){resultState->
            parseState(resultState,{version->
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
        mDatabind.vm = mViewModel
        mViewModel.checkVersion()
        checkNotification()
    }

    override fun onStart() {
        super.onStart()
        val navController = mDatabind.navHost.findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        mDatabind.toolbar.setupWithNavController(navController, appBarConfiguration)
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
            ACTION_SCREENSHOT->{

            }
            ACTION_MANUAL_SIGN->{

            }
        }
    }

    private val settingsLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (isNotificationEnable) {
                startNotificationMonitorService()
            }
        }
    private fun checkNotification(){
        if (!isNotificationEnable) {
            try {
                //打开通知监听设置页面
                val intent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
                settingsLauncher.launch(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            startNotificationMonitorService()
        }
    }

    private fun startNotificationMonitorService() {
        //创建常住通知栏
        createNotification()
        val pm = this.packageManager
        pm.setComponentEnabledSetting(
            ComponentName(this, NotificationMonitorService::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP
        )
        pm.setComponentEnabledSetting(
            ComponentName(this, NotificationMonitorService::class.java),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP
        )
    }
    private fun createNotification(){
        val builder: Notification.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建渠道
            val name: String = this.getResources()
                .getString(R.string.app_name)
            val id = name + "_DefaultChannel"
            val mChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager?.createNotificationChannel(
                mChannel
            )
            builder = Notification.Builder(this, id)
        } else {
            builder = Notification.Builder(this)
        }
        builder.setContentTitle("星尘指令通知监听已打开")
            .setContentText("如果通知消失，请重新开启应用")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(false)
        val notification = builder.build()
        notification.flags = Notification.FLAG_NO_CLEAR
        notificationManager?.notify(111, notification)
    }
}