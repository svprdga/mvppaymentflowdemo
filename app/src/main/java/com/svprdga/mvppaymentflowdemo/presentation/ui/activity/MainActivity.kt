package com.svprdga.mvppaymentflowdemo.presentation.ui.activity

import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.svprdga.mvppaymentflowdemo.R
import kotlinx.android.synthetic.main.activity_main.*

private const val ANIMATION_DURATION_MS = 250L

class MainActivity : AppCompatActivity() {

    // ****************************************** VARS ***************************************** //

    private var windowWidth: Float = 0f

    // *************************************** LIFECYCLE *************************************** //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
