package com.stardust.co.util

import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.stardust.co.model.MailInfo
import java.io.File

object SendMailUtil {
    const val SP_KEY_SENDER_EMAIL = "senderEmail"
    const val SP_KEY_SENDER_AUTH = "senderAuth"
    const val SP_KEY_RECEIVER_EMAIL = "receiverEmail"
    fun send(emailMessage: String) {
        Thread {
            val sp = SPUtils.getInstance()
            val toAddress = sp.getString(SP_KEY_RECEIVER_EMAIL)
            val senderEmail = sp.getString(SP_KEY_SENDER_EMAIL)
            val senderAuth = sp.getString(SP_KEY_SENDER_AUTH)
            MailSender().sendTextMail(
                createMail(
                    toAddress,
                    emailMessage,
                    senderEmail, senderAuth
                )
            )
        }
            .start()
    }

    @JvmStatic
    fun sendAttachFileEmail(toAddress: String, filePath: String?) {
        val file = File(filePath ?: "")
        if (!file.exists()) {
            ToastUtils.showLong("打卡记录不存在，请检查")
            return
        }
        Thread {
            val isSendSuccess = MailSender().sendAccessoryMail(
                createAttachMail(
                    toAddress,
                    file
                )
            )
        }.start()
    }

    fun createMail(
        toAddress: String?,
        emailMessage: String,
        senderEmail: String,
        senderAuth: String
    ): MailInfo {
        val mailInfo = MailInfo()
        mailInfo.mailServerHost = "smtp.qq.com" //发送方邮箱服务器
        mailInfo.mailServerPort = "587" //发送方邮箱端口号
        mailInfo.isValidate = true
        mailInfo.userName = senderEmail // 发送者邮箱地址
        mailInfo.password = senderAuth //邮箱授权码，不是密码
        mailInfo.toAddress = toAddress // 接收者邮箱
        mailInfo.fromAddress = senderEmail // 发送者邮箱
        mailInfo.subject = "指令通知" // 邮件主题
        if (emailMessage == "") {
            mailInfo.content =
                "未监听到打卡成功的通知，请手动登录检查" + TimeUtils.getNowString() // 邮件文本
        } else {
            mailInfo.content = emailMessage // 邮件文本
        }
        return mailInfo
    }

    fun createAttachMail(
        toAddress: String,
        file: File,
        senderEmail: String = "lttclaw@qq.com",
        senderAuth: String = "hwpzapzrkmgpgaba"
    ): MailInfo {
        val mailInfo = MailInfo()
        mailInfo.mailServerHost = "smtp.qq.com" //发送方邮箱服务器
        mailInfo.mailServerPort = "587" //发送方邮箱端口号
        mailInfo.isValidate = true
        mailInfo.userName = senderEmail // 发送者邮箱地址
        mailInfo.password = senderAuth //邮箱授权码，不是密码
        mailInfo.toAddress = toAddress // 接收者邮箱
        mailInfo.fromAddress = senderEmail // 发送者邮箱
        mailInfo.subject = "打卡记录" // 邮件主题
        mailInfo.attachFile = file
        return mailInfo
    }
}