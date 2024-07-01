package com.stardust.co.ui

import android.os.Bundle
import com.stardust.co.databinding.FragmentReceiveCommandBinding
import com.stardust.co.vm.ReceiveCommandVM
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment

class ReceiveCommandFragment: BaseVmDbFragment<ReceiveCommandVM, FragmentReceiveCommandBinding>() {
    override fun createObserver() {

    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
    }

    override fun lazyLoadData() {
    }

    override fun showLoading(message: String) {
    }
}