package com.svprdga.birthdays.di.component

import com.svprdga.mvppaymentflowdemo.di.annotation.PerUiComponent
import com.svprdga.mvppaymentflowdemo.di.module.PresenterModule
import com.svprdga.mvppaymentflowdemo.di.module.UiComponentModule
import com.svprdga.mvppaymentflowdemo.presentation.ui.activity.MainActivity
import com.svprdga.mvppaymentflowdemo.presentation.ui.fragment.AmountFragment
import com.svprdga.mvppaymentflowdemo.presentation.ui.fragment.ContactsFragment
import com.svprdga.mvppaymentflowdemo.presentation.ui.fragment.SubmitFragment
import dagger.Subcomponent

/**
 * Component that will live during the activity/service/fragment lifetime.
 * The [PerUiComponent] tag is a custom scoping annotation to permit objects whose lifetime
 * should conform to the activity's life in that specific component. All these methods are
 * exposed to sub-graphs.
 */
@PerUiComponent
@Subcomponent(modules = [UiComponentModule::class, PresenterModule::class])
interface UiComponent {

    fun inject(target: MainActivity)

    fun inject(target: ContactsFragment)
    fun inject(target: AmountFragment)
    fun inject(target: SubmitFragment)
}
