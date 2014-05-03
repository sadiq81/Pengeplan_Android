package dk.eazyit.pengeplan.android.Pengeplan_Android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.AuthResponse;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Constants;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Transaction;
import dk.eazyit.pengeplan.android.Pengeplan_Android.util.SecurePreferences;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 */
public class PengeplanService extends BroadcastReceiver {

    private static String url = "https://www.pengeplan.dk/api/user/";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        String action = intent.getAction();
        if (Constants.LOGIN_ATTEMPT.equals(action)) {
            authenticate(intent.getExtras().getString(Constants.USERNAME), intent.getExtras().getString(Constants.PASSWORD), intent.getExtras().getString(Constants.PIN));
        } else if (Constants.UPDATE_TRANSACTIONS.equals(action)) {
            getTransactions();
        }

    }

    private void authenticate(final String username, final String password, final String pin) {

        AsyncTask<String, Void, AuthResponse> simpleGetTask = new AsyncTask<String, Void, AuthResponse>() {
            @Override
            protected AuthResponse doInBackground(String... params) {
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                RestTemplate restTemplate = new RestTemplate();
                MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();
                restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);

                try {
                    ResponseEntity<AuthResponse> response = restTemplate.exchange(params[0], HttpMethod.GET, requestEntity, AuthResponse.class);
                    return response.getBody().setUsername(username).setPassword(password).setPin(pin);
                } catch (HttpClientErrorException e) {
                    Log.e("doInBackground - authenticate", e.getLocalizedMessage(), e);
                    return new AuthResponse(false).setUsername(username).setPassword(password).setPin(pin);
                }

            }

            @Override
            protected void onPostExecute(AuthResponse result) {
                Intent intent = new Intent().setAction(Constants.LOGIN_ATTEMPT_COMPLETED);
                intent.putExtra(AuthResponse.PARCELABLE_ID, result);
                context.sendBroadcast(intent);
            }
        };
        simpleGetTask.execute(url + "authenticate/" + username);
    }

    private void getTransactions() {

        String unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        SecurePreferences preferences = new SecurePreferences(context, Constants.LOGIN_PREFERENCES, unique_id, true);
        final String username = preferences.getString(Constants.USERNAME);
        final String password = preferences.getString(Constants.PASSWORD);

        AsyncTask<String, Void, Transaction[]> simpleGetTask = new AsyncTask<String, Void, Transaction[]>() {
            @Override
            protected Transaction[] doInBackground(String... params) {
                HttpAuthentication authHeader = new HttpBasicAuthentication(username, password);
                HttpHeaders requestHeaders = new HttpHeaders();
                requestHeaders.setAuthorization(authHeader);
                HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

                RestTemplate restTemplate = new RestTemplate();
                MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();
                restTemplate.getMessageConverters().add(mappingJacksonHttpMessageConverter);

                try {
                    ResponseEntity<Transaction[]> response = restTemplate.exchange(params[0], HttpMethod.GET, requestEntity, Transaction[].class);
                    return response.getBody();
                } catch (HttpClientErrorException e) {
                    Log.e("doInBackground - getTransactions", e.getLocalizedMessage(), e);
                    return null;
                }

            }

            @Override
            protected void onPostExecute(Transaction[] result) {
                try {
                    DatabaseHelper helper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
                    List<Transaction> list = new ArrayList<Transaction>(Arrays.asList(result));
                    for (Transaction transaction : list) {
                        helper.getTransactionDao().createOrUpdate(transaction);
                    }
                } catch (SQLException e) {
                    Log.e("OnPostExecute - getTransactions", e.getLocalizedMessage(), e);
                } finally {
                    Intent intent = new Intent().setAction(Constants.UPDATED_TRANSACTIONS_COMPLETED);
                    context.sendBroadcast(intent);
                }


            }
        };
        simpleGetTask.execute(url + "transactions/" + username);
    }


}
