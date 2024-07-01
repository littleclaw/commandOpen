package com.stardust.co.ui

import android.os.Bundle
import com.stardust.co.databinding.FragmentSendCommandBinding
import com.stardust.co.vm.SendCommandVM
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

class SendCommandFragment: BaseVmDbFragment<SendCommandVM, FragmentSendCommandBinding>() {
    override fun createObserver() {
    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mViewModel.initReceiver()
        mDatabind.btnSendTest.clickNoRepeat {
            mViewModel.pushCheck()
        }
        mDatabind.btnOpenDing.clickNoRepeat {
            mViewModel.pushSign()
        }
        mDatabind.btnSendGetStatus.clickNoRepeat {
            mViewModel.pushStatusFetch()
        }
    }

    override fun lazyLoadData() {
    }

    override fun showLoading(message: String) {
    }
}