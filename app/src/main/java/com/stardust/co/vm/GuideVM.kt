package com.stardust.co.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.stardust.co.util.SendMailUtil
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

const val SP_KEY_AUTO_NAV = "hostOrTarget"
class GuideVM: BaseViewModel() {
    val targetSettingConfigured = MutableLiveData(false)
    val masterSettingConfigured = MutableLiveData(false)
    val guideTip = MutableLiveData("")
    val navConf = MutableLiveData("")

    fun checkSetting(){
        val sp = SPUtils.getInstance()
        val autoNavSetting = sp.getString(SP_KEY_AUTO_NAV)
        val senderAuth = sp.getString(SendMailUtil.SP_KEY_SENDER_AUTH)
        val receiverRegId = sp.getString(SP_KEY_RECEIVE_REG_ID)
        targetSettingConfigured.value = senderAuth.isNotEmpty()
        masterSettingConfigured.value = receiverRegId.isNotEmpty()
        if (senderAuth.isEmpty() && receiverRegId.isEmpty()){
            guideTip.value = "尚未配置，请根据本机用途选择配置"
        }else if(senderAuth.isNotEmpty() && receiverRegId.isEmpty()){
            guideTip.value = "已配置指令接收端电子邮箱"
        }else if(receiverRegId.isNotEmpty() && senderAuth.isEmpty()){
            guideTip.value= "已配置发送端目标机器注册id"
        }else{
            guideTip.value = "双端都已配置，选择发送或接收视图"
        }
    }
}