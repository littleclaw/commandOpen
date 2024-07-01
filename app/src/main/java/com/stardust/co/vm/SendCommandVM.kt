package com.stardust.co.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.CacheDiskUtils
import com.blankj.utilcode.util.SPUtils
import com.stardust.co.model.BodyMsg
import com.stardust.co.model.PushAudience
import com.stardust.co.model.PushMessage
import com.stardust.co.model.PushResp
import com.stardust.co.network.pushApiService
import com.stardust.co.service.PushCoreService
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

class SendCommandVM : BaseViewModel() {
    val savedRegId = MutableLiveData("")
    val pushResult = MutableLiveData<PushResp>()

    fun initReceiver(){
        val sp = SPUtils.getInstance()
        savedRegId.value = sp.getString(SP_KEY_RECEIVE_REG_ID)
    }

    fun pushCheck() {
        pushCmd(PushCoreService.MSG_MAIL_CHECK)
    }

    fun pushSign() {
        pushCmd(PushCoreService.MSG_SIGN)
    }

    fun pushStatusFetch() {
        pushCmd(PushCoreService.MSG_STATUS_REPORT)
    }

    fun pushScreenShot() {
        pushCmd(PushCoreService.MSG_SCREEN_SHOT)
    }

    fun pushManualSign() {
        pushCmd(PushCoreService.MSG_MANUAL_SIGN)
    }

    private fun pushCmd(cmd: String){
        viewModelScope.launch {
            runCatching {
                val pushMessage = PushMessage()
                pushMessage.audience = PushAudience().apply {
                    registration_id = mutableListOf(savedRegId.value!!)
                }
                pushMessage.message = BodyMsg().apply {
                    msg_content = cmd
                    title = CacheDiskUtils.getInstance().getString("pushRegId")
                }
                pushResult.value = pushApiService.pushMsg(pushMessage)
            }.onFailure {

            }
        }
    }
}