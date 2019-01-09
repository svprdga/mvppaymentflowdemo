package com.svprdga.mvppaymentflowdemo.presentation.presenter.view

enum class ButtonState {
    INVISIBLE,
    ENABLED,
    DISABLED
}

enum class BulletState {
    ENABLED,
    DISABLED
}

interface IMainView : IView {

    fun askForReadContactsPermission()

    fun showPermissionDeniedLayout()

    fun hidePermissionDeniedLayout()

    fun showMainLayouts()

    fun setBackButtonState(state: ButtonState)

    fun setNextButtonState(state: ButtonState)

    fun setStatusText(text: String)

    fun animateContactsToAmount()

    fun animateAmountToSubmit()

    fun animateSubmitToAmount()

    fun animateAmountToContacts()

    fun finish()

    fun closeKeyboard()

    fun setContactsBulletState(state: BulletState)

    fun setAmountBulletState(state: BulletState)

    fun setSubmitBulletState(state: BulletState)

}
