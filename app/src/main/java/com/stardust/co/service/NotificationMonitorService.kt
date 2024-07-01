package com.stardust.co.service

import android.app.Notification
import android.content.ComponentName
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.drake.channel.sendEvent
import com.stardust.co.Constants.DING_PACKAGE_NAME
import com.stardust.co.MainActivity.Companion.TAG_SIGN_SUCCESS

class NotificationMonitorService : NotificationListenerService() {

    /**
     * 有可用的并且和通知管理器连接成功时回调
     */
    override fun onListenerConnected() {

    }

    /**
     * 当有新通知到来时会回调
     */
    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val extras = sbn.notification.extras
        // 获取接收消息APP的包名
        val packageName = sbn.packageName
        // 获取接收消息的内容
        val notificationText = extras.getString(Notification.EXTRA_TEXT)
        if (packageName == DING_PACKAGE_NAME) {
            if (notificationText.isNullOrEmpty()) {
                return
            }
            if (notificationText.contains("考勤打卡")) {
                //保存打卡记录
                //通知发送邮件和更新界面
                sendEvent(notificationText, TAG_SIGN_SUCCESS)
            }
        }else if(packageName == getPackageName()){
            if (notificationText == null || notificationText == "") {
                return
            }
        }
    }

    /**
     * 当有通知移除时会回调
     */
    override fun onNotificationRemoved(sbn: StatusBarNotification) {}
    override fun onListenerDisconnected() {
        // 通知侦听器断开连接 - 请求重新绑定
        requestRebind(ComponentName(this, NotificationListenerService::class.java))
    }
}