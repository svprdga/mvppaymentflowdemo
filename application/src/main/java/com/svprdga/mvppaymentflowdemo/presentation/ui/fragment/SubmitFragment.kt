package com.svprdga.mvppaymentflowdemo.presentation.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.domain.model.ResultContact
import com.svprdga.mvppaymentflowdemo.presentation.custom.ResultListAdapter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.ISubmitPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.ISubmitView
import kotlinx.android.synthetic.main.fragment_submit.*
import java.text.NumberFormat
import javax.inject.Inject

const val TAG_SUBMIT = "submit";

class SubmitFragment : BaseFragment(), ISubmitView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var presenter: ISubmitPresenter

    // ****************************************** VARS ***************************************** //

    private var adapter: ResultListAdapter? = null

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_submit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUiComponent(TAG_SUBMIT).inject(this)
        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun displayResults(results: List<ResultContact>) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = ResultListAdapter(results)
        recyclerView.adapter = adapter
    }

    override fun displayTotalAmount(amount: Float) {
        val format = NumberFormat.getCurrencyInstance()
        totalAmountText.text = "${format.format(amount)}"
    }

}