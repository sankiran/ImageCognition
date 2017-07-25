package com.tutorial.android.apptutorial;

import android.net.Uri;

import com.microsoft.services.msa.OAuthConfig;

/**
 * Created by ssrinath on 7/24/17.
 */

@SuppressWarnings("SpellCheckingInspection")
class MicrosoftOAuth2Endpoint implements OAuthConfig {

    /**
     * The current instance of this class
     */
    private static MicrosoftOAuth2Endpoint sInstance = new MicrosoftOAuth2Endpoint();

    /**
     * The current instance of this class
     *
     * @return The instance
     */
    static MicrosoftOAuth2Endpoint getInstance() {
        return sInstance;
    }

    @Override
    public Uri getAuthorizeUri() {
        return Uri.parse("https://login.microsoftonline.com/common/oauth2/v2.0/authorize");
    }

    @Override
    public Uri getDesktopUri() {
        return Uri.parse("urn:ietf:wg:oauth:2.0:oob");
    }

    @Override
    public Uri getLogoutUri() {
        return Uri.parse("https://login.microsoftonline.com/common/oauth2/v2.0/logout");
    }

    @Override
    public Uri getTokenUri() {
        return Uri.parse("https://login.microsoftonline.com/common/oauth2/v2.0/token");
    }
}
