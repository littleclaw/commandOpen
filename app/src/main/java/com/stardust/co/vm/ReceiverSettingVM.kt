package com.stardust.co.vm

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.SPUtils
import com.pengxh.autodingding.utils.toast
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

const val SP_KEY_RECEIVE_REG_ID = "receiveRegId"
class ReceiverSettingVM : BaseViewModel() {
    val receiverReg = MutableLiveData("")

    fun saveRegId(){
        val sp = SPUtils.getInstance()
        sp.put(SP_KEY_RECEIVE_REG_ID, receiverReg.value)
        toast("目标注册ID已保存")
    }
}