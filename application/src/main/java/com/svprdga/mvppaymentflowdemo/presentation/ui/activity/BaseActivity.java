package com.svprdga.mvppaymentflowdemo.presentation.ui.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.svprdga.birthdays.di.component.AppComponent;
import com.svprdga.birthdays.di.component.UiComponent;
import com.svprdga.mvppaymentflowdemo.di.module.PresenterModule;
import com.svprdga.mvppaymentflowdemo.di.module.UiComponentModule;
import com.svprdga.mvppaymentflowdemo.presentation.ui.application.CoreApp;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public abstract class BaseActivity extends AppCompatActivity implements IdlingResource {

    // ****************************************** VARS ***************************************** //

    protected UiComponent mUiComponent;
    protected Handler mActivityHandler;

    private boolean mIdle = false;
    private IdlingResource.ResourceCallback mIdleCallback;
    private List<String> mIdleResources = new ArrayList<>();

    // ************************************* INNER CLASSES ************************************* //

    public interface KeyboardVisibilityListener {
        /**
         * Know when the soft keyboard opens and closes.
         *
         * @param keyboardVisible True when the keyboard opens, false when it closes.
         */
        void onKeyboardVisibilityChanged(boolean keyboardVisible);
    }

    // *************************************** LIFECYCLE *************************************** //

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        mUiComponent = getCoreApp().getUiComponent();

        mActivityHandler = new Handler();
        mIdleResources = new ArrayList<>();
        setIdle(false);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        releaseUiComponent();
    }

    // ************************************* PUBLIC METHODS ************************************ //

    /**
     * Get the height of the status bar.
     *
     * @return Bar height.
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public String getName(){
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow(){
        return mIdle && mIdleResources.isEmpty();
    }

    @Override
    public void registerIdleTransitionCallback(IdlingResource.ResourceCallback callback){
        mIdleCallback = callback;
    }

    /**
     * Removes a resource from the idle resources list.
     */
    public void unregisterIdleResource(String identifier){
        mIdleResources.remove(identifier);
        updateIdle();
    }

    /**
     * Add a resource to the idle resources list.
     * @param identifier Identifier of the idling resource to register.
     */
    public void registerIdleResource(String identifier){

        mIdleResources.add(identifier);
        updateIdle();
    }

    /**
     * Set the idle state.
     * @param idle Idle value.
     */
    public void setIdle(boolean idle) {
        if (mIdle != idle) {
            mIdle = idle;
            if (idle &&mIdleCallback != null) {
                mIdleCallback.onTransitionToIdle();
            }
        }
    }

    /**
     * Add a keyboard listener to receive an event callback when the soft keyboard opens and closes.
     *
     * @param activity                   Activity to watch.
     * @param keyboardVisibilityListener Implement this listener to receive event callbacks.
     */
    public static void setKeyboardVisibilityListener(Activity activity, final
    KeyboardVisibilityListener keyboardVisibilityListener) {
        final View contentView = activity.findViewById(android.R.id.content);
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                .OnGlobalLayoutListener() {
            private int mPreviousHeight;

            @Override
            public void onGlobalLayout() {
                int newHeight = contentView.getHeight();
                if (mPreviousHeight != 0) {
                    if (mPreviousHeight > newHeight) {
                        // Height decreased: keyboard was shown
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(true);
                    } else if (mPreviousHeight < newHeight) {
                        // Height increased: keyboard was hidden
                        keyboardVisibilityListener.onKeyboardVisibilityChanged(false);
                    }
                }
                mPreviousHeight = newHeight;
            }
        });
    }

    /**
     * Check if a given service is running.
     * @param serviceClass Service class object.
     * @return True if the provided service is running, false otherwise.
     */
    public boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {

            if (serviceClass.getName().equals(service.service.getClassName())){
                return true;
            }

        }
        return false;
    }

    /**
     * Get the {@link CoreApp}.
     * @return {@link CoreApp} instance.
     */
    public CoreApp getCoreApp(){
        return (CoreApp) getApplication();
    }

    /**
     * Method that shows a toast with the required message and duration.
     *
     * @param message Message to be displayed.
     * @param duration  Duration of the toast.
     */
    public void showToast(String message, int duration) {
        Toast.makeText(getApplicationContext(), message, duration).show();
    }

    // *********************************** PROTECTED METHODS *********************************** //

    // Dependency injection.

    /**
     * Method that provides the activity component.
     *
     * @param tag Tag used to log.
     * @return UiComponent instance.
     */
    protected UiComponent getUiComponent(String tag) {
        if (mUiComponent == null) {
            mUiComponent = getAppComponent().plusUiComponent(
                    new UiComponentModule(this, null, tag),
                    new PresenterModule());
        }
        return mUiComponent;
    }

    /**
     * Check the idle state of this activity.
     * @return True if the activity is idle, false otherwise.
     */
    protected boolean isIdle(){
        return mIdle;
    }

    /**
     * Method that close the keyboard.
     */
    protected void closeKeyboard() {

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            view.clearFocus();
        }
    }


    // ************************************ PRIVATE METHODS ************************************ //

    /**
     * Method that retrieves the current App Component.
     *
     * @return Current {@link AppComponent} instance.
     */
    public AppComponent getAppComponent() {
        return getCoreApp().getAppComponent();
    }

    /**
     * Updates the idle state.
     */
    private void updateIdle(){
        setIdle(mIdleResources.isEmpty());
    }

    /**
     * Method that releases {@link UiComponent}. Always use this method in the end of the life
     * of the object whose has created an {@link UiComponent}.
     */
    private void releaseUiComponent() {
        mUiComponent = null;
    }

}