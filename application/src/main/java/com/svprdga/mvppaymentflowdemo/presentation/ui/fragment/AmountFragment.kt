package com.svprdga.mvppaymentflowdemo.presentation.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IAmountPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IAmountView
import kotlinx.android.synthetic.main.fragment_amount.*
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

        setListeners()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unBind()
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun setListeners() {
        amountInput.addTextChangedListener(textWatcher)
        amountInput.setOnEditorActionListener(keyboardListener)
    }

    // ************************************** UI LISTENERS ************************************* //

    private val textWatcher
        get() = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Nothing.
            }

            override fun onTextChanged(text: CharSequence, start: Int, before: Int, count: Int) {
                presenter.newValueEntered(text.toString())
            }

            override fun afterTextChanged(editable: Editable) {
                // Nothing.
            }
        }

    private val keyboardListener
        get() = TextView.OnEditorActionListener { _, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER
                || actionId == EditorInfo.IME_ACTION_DONE) {
                presenter.keyboardEnterAction()
                return@OnEditorActionListener true
            }
            false
        }


}