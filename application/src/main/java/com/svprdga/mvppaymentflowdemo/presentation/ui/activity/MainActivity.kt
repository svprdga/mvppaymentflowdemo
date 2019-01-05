package com.svprdga.mvppaymentflowdemo.presentation.ui.activity

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val TAG_MAIN = "main"

private const val ANIMATION_DURATION_MS = 250L

class MainActivity : BaseActivity(), IMainView {

    // ************************************* INJECTED VARS ************************************* //

    @Inject
    lateinit var presenter: IMainPresenter

    // ****************************************** VARS ***************************************** //

    private var windowWidth: Float = 0f

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // DI.
        getUiComponent(TAG_MAIN).inject(this)

        calculateWindowWidth()
        prepareLayoutsForAnimations()

        val handler = Handler()
        handler.postDelayed({
            animateContactsToAmount()
        }, 3000)
        handler.postDelayed({
            animateAmountToSubmit()
        }, 6000)
        handler.postDelayed({
            animateSubmitToAmount()
        }, 9000)
        handler.postDelayed({
            animateAmountToContacts()
        }, 12000)

        presenter.bind(this)
    }

    public override fun onStart() {
        super.onStart()

        presenter.onStartView()
    }

    public override fun onStop() {
        super.onStop()

        presenter.onStopView()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unBind()
    }

    // ************************************ PRIVATE METHODS ************************************ //

    private fun calculateWindowWidth() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        windowWidth = size.x.toFloat()
    }

    private fun prepareLayoutsForAnimations() {
        amountFrame.x = windowWidth
        submitFrame.x = windowWidth
    }

    private fun animateContactsToAmount() {
        contactFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
        amountFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    private fun animateAmountToSubmit() {
        amountFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
        submitFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    private fun animateSubmitToAmount() {
        submitFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
        amountFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    private fun animateAmountToContacts() {
        amountFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
        contactFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
    }
}
