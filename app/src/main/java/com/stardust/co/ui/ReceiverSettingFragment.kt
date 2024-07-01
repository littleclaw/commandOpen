package com.stardust.co.ui

import android.os.Bundle
import com.stardust.co.databinding.FragmentReceiverSettingBinding
import com.stardust.co.vm.ReceiverSettingVM
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.ext.nav

class ReceiverSettingFragment: BaseVmDbFragment<ReceiverSettingVM, FragmentReceiverSettingBinding>() {
    override fun createObserver() {

    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.btnSaveReg.setOnClickListener {
            mViewModel.saveRegId()
//            nav().navigate()
        }
    }

    override fun lazyLoadData() {

    }

    override fun showLoading(message: String) {
    }
}