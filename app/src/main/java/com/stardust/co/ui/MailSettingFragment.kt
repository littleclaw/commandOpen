package com.stardust.co.ui

import android.os.Bundle
import com.stardust.co.databinding.FragmentMailSettingBinding
import com.stardust.co.vm.MailSettingVM
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

class MailSettingFragment: BaseVmDbFragment<MailSettingVM, FragmentMailSettingBinding>() {
    override fun createObserver() {
        mViewModel.senderEmail.observe(this){
            mViewModel.checkValid()
        }
        mViewModel.senderAuth.observe(this){
            mViewModel.checkValid()
        }
        mViewModel.receiverEmail.observe(this){
            mViewModel.checkValid()
        }
    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mViewModel.initSetting()
        mDatabind.btnSaveMailConf.setOnClickListener {
            mViewModel.saveSetting()
            //nav()
        }
        mDatabind.btnTestMailConf.clickNoRepeat {
            mViewModel.testSetting()
        }
        mDatabind.btnCopy2Clipboard.clickNoRepeat {
            mViewModel.copyRegId2Clipboard()
        }
    }

    override fun lazyLoadData() {
    }

    override fun showLoading(message: String) {
    }
}