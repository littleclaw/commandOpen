package com.stardust.co.ui

import android.os.Bundle
import com.pengxh.autodingding.utils.toast
import com.stardust.co.R
import com.stardust.co.databinding.FragmentReceiverSettingBinding
import com.stardust.co.vm.ReceiverSettingVM
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

class ReceiverSettingFragment: BaseVmDbFragment<ReceiverSettingVM, FragmentReceiverSettingBinding>() {
    override fun createObserver() {

    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.btnSaveReg.clickNoRepeat {
            mViewModel.saveRegId()
            toast("已保存设置")
            nav().navigate(R.id.action_receiverSettingFragment_to_guideFragment)
        }
    }

    override fun lazyLoadData() {

    }

    override fun showLoading(message: String) {
    }
}