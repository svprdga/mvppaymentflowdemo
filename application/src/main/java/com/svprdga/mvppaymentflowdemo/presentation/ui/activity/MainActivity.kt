package com.svprdga.mvppaymentflowdemo.presentation.ui.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.svprdga.mvppaymentflowdemo.R
import com.svprdga.mvppaymentflowdemo.presentation.presenter.abstraction.IMainPresenter
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
        setSupportActionBar(toolbar)

        // Prepare animations.
        calculateWindowWidth()
        prepareLayoutsForAnimations()

        setListeners()

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

    // ************************************* PUBLIC METHODS ************************************ //

    override fun askForReadContactsPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSION_READ_CONTACTS)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {

        if (requestCode == PERMISSION_READ_CONTACTS) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.readContactsPermissionGranted()
            } else {
                presenter.readContactsPermissionDenied()
            }
        }
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

    private fun setListeners() {
        allowContactsPermissionButton.setOnClickListener(allowContactsPermissionListener)
    }

    // ************************************** UI LISTENERS ************************************* //

    private val allowContactsPermissionListener: View.OnClickListener
        get() = View.OnClickListener { presenter.askForContactsPermissionClick() }
}
