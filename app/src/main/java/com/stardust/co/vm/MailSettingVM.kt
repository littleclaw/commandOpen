package com.stardust.co.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.SPUtils
import com.pengxh.autodingding.utils.toast
import com.stardust.co.CoApplication
import com.stardust.co.util.MailSender
import com.stardust.co.util.SendMailUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class MailSettingVM : BaseViewModel() {
    val senderEmail = MutableLiveData("")
    val senderAuth = MutableLiveData("")
    val receiverEmail = MutableLiveData("")
    val regId = MutableLiveData("")
    val inputValid = MutableLiveData(false)

    fun testSetting() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val emailMessage = "测试邮件"
                val mailInfo = SendMailUtil.createMail(
                    receiverEmail.value,
                    emailMessage,
                    senderEmail.value!!,
                    senderAuth.value!!
                )
                MailSender().sendTextMail(mailInfo)
                toast("已尝试发送，请检查是否收到测试邮件")
            }
        }
    }

    fun checkValid() {
        inputValid.value =
            (senderEmail.value.isNullOrEmpty() || senderAuth.value.isNullOrEmpty() || receiverEmail.value.isNullOrEmpty()).not()
    }

    fun initSetting() {
        val sp = SPUtils.getInstance()
        senderEmail.value = sp.getString(SendMailUtil.SP_KEY_SENDER_EMAIL)
        senderAuth.value = sp.getString(SendMailUtil.SP_KEY_SENDER_AUTH)
        receiverEmail.value = sp.getString(SendMailUtil.SP_KEY_RECEIVER_EMAIL)
        regId.value = JPushInterface.getRegistrationID(CoApplication.instance)
        checkValid()
    }

    fun copyRegId2Clipboard() {
        ClipboardUtils.copyText(regId.value)
        toast("已复制")
    }

    fun saveSetting() {
        val sp = SPUtils.getInstance()
        sp.put(SendMailUtil.SP_KEY_SENDER_EMAIL, senderEmail.value)
        sp.put(SendMailUtil.SP_KEY_SENDER_AUTH, senderAuth.value)
        sp.put(SendMailUtil.SP_KEY_RECEIVER_EMAIL, receiverEmail.value)
    }
}