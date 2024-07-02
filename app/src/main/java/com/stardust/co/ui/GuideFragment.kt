package com.stardust.co.ui

import android.os.Bundle
import com.blankj.utilcode.util.SPUtils
import com.stardust.co.R
import com.stardust.co.databinding.FragmentGuideBinding
import com.stardust.co.vm.GuideVM
import com.stardust.co.vm.SP_KEY_AUTO_NAV
import me.hgj.jetpackmvvm.base.fragment.BaseVmDbFragment
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat

const val CO_HOST = "commandHost"
const val CO_TARGET = "commandTarget"
class GuideFragment: BaseVmDbFragment<GuideVM, FragmentGuideBinding>() {
    override fun createObserver() {
        mViewModel.navConf.observe(this){
            when(it){
                CO_HOST->{
//                    nav().navigate(R.id.action_guideFragment_to_sendCommandFragment)
                }
                CO_TARGET->{
//                    nav().navigate(R.id.action_guideFragment_to_receiveCommandFragment)
                }
            }
        }
    }

    override fun dismissLoading() {
    }

    override fun onStart() {
        super.onStart()
        mViewModel.checkSetting()
    }
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mDatabind.btnGoEmailSetting.clickNoRepeat {
            nav().navigate(R.id.mailSettingFragment)
        }
        mDatabind.btnGoRegIdSetting.clickNoRepeat {
            nav().navigate(R.id.receiverSettingFragment)
        }
        mDatabind.btnSetAsHost.clickNoRepeat {
            nav().navigate(R.id.action_guideFragment_to_sendCommandFragment)
            val sp = SPUtils.getInstance()
            sp.put(SP_KEY_AUTO_NAV, CO_HOST)
        }
        mDatabind.btnSetAsTarget.clickNoRepeat {
            nav().navigate(R.id.action_guideFragment_to_receiveCommandFragment)
            val sp = SPUtils.getInstance()
            sp.put(SP_KEY_AUTO_NAV, CO_TARGET)
        }
    }

    override fun lazyLoadData() {
    }

    override fun showLoading(message: String) {
    }

}