package dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author
 */
public class AuthResponse implements Parcelable {

    public static String PARCELABLE_ID = "authResponse";

    private boolean authorized;
    private String username;
    private String password;
    private String pin;

    public AuthResponse() {
    }

    public AuthResponse(boolean authorized) {
        this.authorized = authorized;
    }

    public AuthResponse(boolean authorized, String username, String password, String pin) {
        this.authorized = authorized;
        this.username = username;
        this.password = password;
        this.pin = pin;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public AuthResponse setAuthorized(boolean authorized) {
        this.authorized = authorized;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public AuthResponse setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthResponse setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPin() {
        return pin;
    }

    public AuthResponse setPin(String pin) {
        this.pin = pin;
        return this;
    }

    public AuthResponse(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        this.authorized = Boolean.parseBoolean(data[0]);
        this.username = data[1];
        this.password = data[2];
        this.pin = data[3];
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.authorized),
                this.username,
                this.password,
                this.pin
        });
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public AuthResponse createFromParcel(Parcel in) {
            return new AuthResponse(in);
        }

        public AuthResponse[] newArray(int size) {
            return new AuthResponse[size];
        }
    };
}
