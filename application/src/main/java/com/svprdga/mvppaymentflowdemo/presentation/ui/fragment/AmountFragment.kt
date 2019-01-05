package com.svprdga.mvppaymentflowdemo.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IAmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IAmountView
import javax.inject.Inject

const val TAG_AMOUNT = "amount";

class AmountFragment : BaseFragment(), IAmountView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var presenter: IAmountPresenter

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_amount, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUiComponent(TAG_AMOUNT).inject(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unBind()
    }

}