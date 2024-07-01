package com.stardust.co.vm

import androidx.lifecycle.MutableLiveData
import com.stardust.co.model.Version
import com.stardust.co.network.apiService
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.request
import me.hgj.jetpackmvvm.state.ResultState

class MainVM : BaseViewModel() {
    val versionResult = MutableLiveData<ResultState<Version>>()

    fun checkVersion(){
        request({ apiService.getVersion()}, versionResult)
    }
}