package dk.eazyit.pengeplan.android.Pengeplan_Android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import dk.eazyit.pengeplan.android.Pengeplan_Android.R;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.AuthResponse;
import dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain.Constants;

public class Login extends PengeplanActivity {
    /**
     * Called when the activity is first created.
     */

    String pin_check_string;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Intent intent = new Intent();
        intent.setAction(Constants.TEST_FOR_PERSISTENT_LOGIN);
        sendBroadcast(intent);
    }

    public void login(View view) {

        EditText username = (EditText) findViewById(R.id.username);

        TextView password = (TextView) findViewById(R.id.password);
        TextView pin = (TextView) findViewById(R.id.pin);

        if (pin_check_string != null && !pin.getText().toString().equals(pin_check_string)) {
            CharSequence text = "Wrong pin";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(view.getContext(), text, duration);
            toast.show();
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Constants.LOGIN_ATTEMPT);
        intent.putExtra(Constants.USERNAME, username.getText().toString());
        intent.putExtra(Constants.PASSWORD, password.getText().toString());
        intent.putExtra(Constants.PIN, pin.getText().toString());
        sendBroadcast(intent);
    }

    public void remember(View view) {
        boolean on = ((Switch) view).isChecked();

        TextView pin_label = (TextView) findViewById(R.id.pin_label);
        TextView pin = (TextView) findViewById(R.id.pin);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);


        if (on) {
            pin_label.setVisibility(View.VISIBLE);
            pin.setVisibility(View.VISIBLE);
        } else {
            pin_label.setVisibility(View.INVISIBLE);
            pin.setVisibility(View.INVISIBLE);
            username.setText("");
            password.setText("");
            username.setEnabled(true);
            password.setEnabled(true);
            pin.setText("");
            pin_check_string = null;

            Intent intent = new Intent();
            intent.setAction(Constants.CLEAR_LOGIN);
            sendBroadcast(intent);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mMessageReceiver, new IntentFilter(Constants.LOGIN_ATTEMPT_COMPLETED));
        registerReceiver(mMessageReceiver, new IntentFilter(Constants.PERSISTENT_LOGIN_EXISTS));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent

            String action = intent.getAction();

            if (Constants.LOGIN_ATTEMPT_COMPLETED.equals(action)) {
                AuthResponse response = intent.getExtras().getParcelable(AuthResponse.PARCELABLE_ID);
                if (response.isAuthorized()) {
                    startActivity(new Intent(Login.this, Overview.class));
                    Login.this.finish();
                } else {
                    CharSequence text = "Login failed \n Wrong username or password";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            } else if (Constants.PERSISTENT_LOGIN_EXISTS.equals(action)) {
                TextView username = (TextView) findViewById(R.id.username);
                TextView password = (TextView) findViewById(R.id.password);

                username.setText(intent.getExtras().getString(Constants.USERNAME));
                password.setText(intent.getExtras().getString(Constants.PASSWORD));

                TextView pin_label = (TextView) findViewById(R.id.pin_label);
                TextView pin = (TextView) findViewById(R.id.pin);
                pin_label.setVisibility(View.VISIBLE);
                pin.setVisibility(View.VISIBLE);
                pin_check_string = intent.getExtras().getString(Constants.PIN);
                Switch remember = (Switch) findViewById(R.id.remember);
                remember.setChecked(true);
            }
        }
    };


}
