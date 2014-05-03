package dk.eazyit.pengeplan.android.Pengeplan_Android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.AuthResponse;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Constants;
import dk.eazyit.pengeplan.android.Pengeplan_Android.util.SecurePreferences;

/**
 * @author
 */
public class LoginService extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();

        if (Constants.LOGIN_ATTEMPT_COMPLETED.equals(action)) {
            AuthResponse response = intent.getExtras().getParcelable(AuthResponse.PARCELABLE_ID);
            saveLogin(response);
        } else if (Constants.CLEAR_LOGIN.equals(action)) {
            clearLogin();
        } else if (Constants.TEST_FOR_PERSISTENT_LOGIN.equals(action)) {
            testForPersistentLogin();
        }

    }

    private void testForPersistentLogin() {
        String unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        SecurePreferences preferences = new SecurePreferences(context, Constants.LOGIN_PREFERENCES, unique_id, true);

        String pin = preferences.getString(Constants.PIN);
        if (pin != null) {
            String username = preferences.getString(Constants.USERNAME);
            String password = preferences.getString(Constants.PASSWORD);
            Intent intent = new Intent();
            intent.setAction(Constants.PERSISTENT_LOGIN_EXISTS);
            intent.putExtra(Constants.USERNAME, username);
            intent.putExtra(Constants.PASSWORD, password);
            intent.putExtra(Constants.PIN, pin);
            context.sendBroadcast(intent);
        }
        context = null;
    }

    private void clearLogin() {
        String unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        SecurePreferences preferences = new SecurePreferences(context, Constants.LOGIN_PREFERENCES, unique_id, true);
        preferences.put(Constants.USERNAME, null);
        preferences.put(Constants.PASSWORD, null);
        preferences.put(Constants.PIN, null);

        context = null;
    }

    private void saveLogin(AuthResponse response) {
        if (response.isAuthorized()) {

            String unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            SecurePreferences preferences = new SecurePreferences(context, Constants.LOGIN_PREFERENCES, unique_id, true);
            preferences.put(Constants.USERNAME, response.getUsername());
            preferences.put(Constants.PASSWORD, response.getPassword());

            if (response.getPin().length() >= 4) {
                preferences.put(Constants.PIN, response.getPin());
            }

        }
        context = null;

    }


}
