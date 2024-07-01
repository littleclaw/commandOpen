package com.stardust.co.ui

import android.os.Bundle
import com.stardust.co.R
import com.stardust.co.databinding.FragmentGuideBinding
import com.stardust.co.vm.GuideVM
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
                    nav().navigate(R.id.sendCommandFragment)
                }
                CO_TARGET->{

                }
            }
        }
    }

    override fun dismissLoading() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.vm = mViewModel
        mViewModel.checkSetting()
        mDatabind.btnGoEmailSetting.clickNoRepeat {
            nav().navigate(R.id.mailSettingFragment)
        }
        mDatabind.btnGoRegIdSetting.clickNoRepeat {
            nav().navigate(R.id.receiverSettingFragment)
        }
        mDatabind.btnSetAsHost.clickNoRepeat {
            nav().navigate(R.id.sendCommandFragment)
        }
        mDatabind.btnSetAsTarget.clickNoRepeat {
            nav().navigate(R.id.receiveCommandFragment)
        }
    }

    override fun lazyLoadData() {
    }

    override fun showLoading(message: String) {
    }

}