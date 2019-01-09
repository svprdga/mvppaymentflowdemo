package com.svprdga.mvppaymentflowdemo.presentation.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.WindowManager
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.BulletState
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.ButtonState
import com.svprdga.mvppaymentflowdemo.presentation.presenter.view.IMainView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

const val TAG_MAIN = "main"

private const val ANIMATION_DURATION_MS = 250L

private const val PERMISSION_READ_CONTACTS = 1001

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

        // Toolbar.
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.primaryTextDark))
        setSupportActionBar(toolbar)

        // Prevent the keyboard from opening at startup.
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Prepare animations.
        calculateWindowWidth()
        prepareLayoutsForAnimations()

        setListeners()

        presenter.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.unBind()
    }

    // ************************************* PUBLIC METHODS ************************************ //

    override fun askForReadContactsPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSION_READ_CONTACTS
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {

        if (requestCode == PERMISSION_READ_CONTACTS) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.readContactsPermissionGranted()
            } else {
                presenter.readContactsPermissionDenied()
            }
        }
    }

    override fun onBackPressed() {
        presenter.backPressed()
    }

    override fun showPermissionDeniedLayout() {
        noPermissionsLayout.visibility = View.VISIBLE
    }

    override fun hidePermissionDeniedLayout() {
        noPermissionsLayout.visibility = View.GONE
    }

    override fun showMainLayouts() {
        mainLayouts.visibility = View.VISIBLE
    }

    override fun setBackButtonState(state: ButtonState) {
        when (state) {
            ButtonState.DISABLED -> {
                backButton.visibility = View.VISIBLE
                backButton.isEnabled = false
                backButton.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.hintDisabledTextDark)
                )
            }
            ButtonState.ENABLED -> {
                backButton.visibility = View.VISIBLE
                backButton.isEnabled = true
                backButton.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.primaryTextDark)
                )
            }
            else -> {
                backButton.visibility = View.INVISIBLE
            }
        }
    }

    override fun setNextButtonState(state: ButtonState) {
        when (state) {
            ButtonState.DISABLED -> {
                nextButton.visibility = View.VISIBLE
                nextButton.isEnabled = false
                nextButton.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.hintDisabledTextDark)
                )
            }
            ButtonState.ENABLED -> {
                nextButton.visibility = View.VISIBLE
                nextButton.isEnabled = true
                nextButton.imageTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this, R.color.primaryTextDark)
                )
            }
            else -> {
                nextButton.visibility = View.INVISIBLE
            }
        }
    }

    override fun setStatusText(text: String) {
        statusText.text = text
    }

    override fun animateContactsToAmount() {
        contactFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
        amountFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    override fun animateAmountToSubmit() {
        amountFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
        submitFrame.animate()
            .translationXBy(-windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    override fun animateSubmitToAmount() {
        submitFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
        amountFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    override fun animateAmountToContacts() {
        amountFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
        contactFrame.animate()
            .translationXBy(windowWidth)
            .duration = ANIMATION_DURATION_MS
    }

    override fun finish() {
        super.finish()
    }

    override fun closeKeyboard() {
        super.closeKeyboard()
    }

    override fun setContactsBulletState(state: BulletState) {
        when (state) {
            BulletState.ENABLED ->
                contactsBullet.background =
                        ContextCompat.getDrawable(this, R.drawable.shape_bullet_active)
            else ->
                contactsBullet.background =
                        ContextCompat.getDrawable(this, R.drawable.shape_bullet_inactive)
        }
    }

    override fun setAmountBulletState(state: BulletState) {
        when (state) {
            BulletState.ENABLED ->
                amountBullet.background =
                        ContextCompat.getDrawable(this, R.drawable.shape_bullet_active)
            else ->
                amountBullet.background =
                        ContextCompat.getDrawable(this, R.drawable.shape_bullet_inactive)
        }
    }

    override fun setSubmitBulletState(state: BulletState) {
        when (state) {
            BulletState.ENABLED ->
                submitBullet.background =
                        ContextCompat.getDrawable(this, R.drawable.shape_bullet_active)
            else ->
                submitBullet.background =
                        ContextCompat.getDrawable(this, R.drawable.shape_bullet_inactive)
        }
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

    private fun setListeners() {
        allowContactsPermissionButton.setOnClickListener(allowContactsPermissionListener)
        nextButton.setOnClickListener(nextListener)
        backButton.setOnClickListener(backListener)
    }

    // ************************************** UI LISTENERS ************************************* //

    private val allowContactsPermissionListener: View.OnClickListener
        get() = View.OnClickListener { presenter.askForContactsPermissionClick() }

    private val nextListener: View.OnClickListener
        get() = View.OnClickListener { presenter.clickNext() }

    private val backListener: View.OnClickListener
        get() = View.OnClickListener { presenter.clickBack() }
}
