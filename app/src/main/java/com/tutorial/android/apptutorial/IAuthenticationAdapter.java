package com.tutorial.android.apptutorial;

import android.app.Activity;

import com.microsoft.graph.authentication.IAuthenticationProvider;
import com.microsoft.graph.concurrency.ICallback;

/**
 * Created by ssrinath on 7/24/17.
 */

public interface IAuthenticationAdapter extends IAuthenticationProvider {

    /**
     * Logs out the user
     *
     * @param callback The callback when the logout is complete or an error occurs
     */
    void logout(final ICallback<Void> callback);

    /**
     * Login a user by popping UI
     *
     * @param activity The current activity
     * @param callback The callback when the login is complete or an error occurs
     */
    void login(final Activity activity, final ICallback<Void> callback);

    /**
     * Login a user with no ui
     *
     * @param callback The callback when the login is complete or an error occurs
     */
    void loginSilent(final ICallback<Void> callback);
}
