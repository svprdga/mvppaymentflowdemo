package com.svprdga.mvppaymentflowdemo.presentation.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.svprdga.birthdays.di.component.AppComponent;
import com.svprdga.birthdays.di.component.UiComponent;
import com.svprdga.mvppaymentflowdemo.di.module.PresenterModule;
import com.svprdga.mvppaymentflowdemo.di.module.UiComponentModule;
import com.svprdga.mvppaymentflowdemo.presentation.ui.activity.BaseActivity;
import com.svprdga.mvppaymentflowdemo.presentation.ui.application.CoreApp;

/**
 * Base fragment class for all fragments.
 */
public abstract class BaseFragment extends Fragment {

    // ****************************************** VARS ***************************************** //

    protected UiComponent uiComponent;

    // *************************************** LIFECYCLE *************************************** //

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        uiComponent = getCoreApp().getUiComponent();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        releaseUiComponent();
    }

    // *********************************** PROTECTED METHODS *********************************** //

    /**
     * Shortcut for getView().findViewById().
     * @param viewId Id of the view to find.
     * @return Found {@link View}.
     */
    protected View findViewById(int viewId){
        return getView().findViewById(viewId);
    }

    /**
     * Method that provides the activity component.
     *
     * @param tag Tag used to log.
     * @return UiComponent instance.
     */
    protected UiComponent getUiComponent(String tag) {
        if (uiComponent == null) {
            uiComponent = getAppComponent().plusUiComponent(
                    new UiComponentModule(null, this, tag),
                    new PresenterModule());
        }
        return uiComponent;
    }

    /**
     * Get the associated {@link BaseActivity}.
     * @return {@link BaseActivity}.
     */
    protected BaseActivity getBase(){
        return (BaseActivity) getActivity();
    }

    /**
     * Get the {@link CoreApp}.
     * @return {@link CoreApp} instance.
     */
    protected CoreApp getCoreApp(){
        return getBase().getCoreApp();
    }

    // ************************************ PRIVATE METHODS ************************************ //

    /**
     * Method that retrieves the current App Component.
     *
     * @return Current {@link AppComponent} instance.
     */
    private AppComponent getAppComponent() {
        return getCoreApp().getAppComponent();
    }

    /**
     * Method that releases {@link UiComponent}. Always use this method in the end of the life
     * of the object whose has created an {@link UiComponent}.
     */
    private void releaseUiComponent() {
        uiComponent = null;
    }

}
