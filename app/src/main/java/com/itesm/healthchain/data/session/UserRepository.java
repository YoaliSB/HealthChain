package com.itesm.healthchain.data.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository implements LoginStateListener, LogoutStateListener, PasswordChangeStateListener {

    private Context context;
    private LoginDataSource loginDataSource;
    private LogoutDataSource logoutDataSource;
    private LoginStateListener loginListener;
    private LogoutStateListener logoutListener;
    private PasswordDataSource passwordDataSource;
    private PasswordChangeStateListener passwordChangeListener;

    public UserRepository(final Context context) {
        this.context = context;
        this.loginDataSource = new LoginDataSource(context);
        this.logoutDataSource = new LogoutDataSource(context);
        this.passwordDataSource = new PasswordDataSource(context);
        loginDataSource.setLoginStateListener(this);
        logoutDataSource.setLogoutStateListener(this);
        passwordDataSource.setPasswordListener(this);
    }

    public boolean isLoggedIn() {
        String role = SharedPreferencesManager.getRole(context);
        String token = SharedPreferencesManager.getToken(context);
        return role != null && token != null && !role.isEmpty() && !token.isEmpty();
    }

    public void logout() {
        logoutDataSource.logout();
    }

    public void login(String username, String password) {
        loginDataSource.login(username, password);
    }

    public void setLoginListener(LoginStateListener loginStateListener) {
        this.loginListener = loginStateListener;
    }

    public void setLogoutListener(LogoutStateListener logoutStateListener) {
        this.logoutListener = logoutStateListener;
    }

    public void setInfoAsCompleted(){
        loginDataSource.updateInfoAsCompleted();
    }

    public boolean infoIsCompleted(){
        return loginDataSource.infoIsCompleted();
    }

    @Override
    public void onLoginSuccess(LoggedInUser user) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preferences_key),Context.MODE_PRIVATE);
        // TODO: Encrypted shared data
        SharedPreferencesManager.setToken(context, user.getToken());
        SharedPreferencesManager.setRole(context, user.getRole());
        SharedPreferencesManager.setName(context, user.getName());

        // Callback to activity for redirection
        loginListener.onLoginSuccess(user);
    }

    @Override
    public void onLoginFailure() {
        loginListener.onLoginFailure();
    }

    @Override
    public void onLogoutSuccess() {
        SharedPreferencesManager.setToken(context, null);
        SharedPreferencesManager.setRole(context, null);
        SharedPreferencesManager.setName(context, null);
        logoutListener.onLogoutSuccess();
    }

    @Override
    public void onLogoutFailure() {
        logoutListener.onLogoutFailure();
    }

    public void updatePassword(String oldPassword, String newPassword){
        passwordDataSource.updatePassword(oldPassword, newPassword);
    }

    public void setPasswordListener(PasswordChangeStateListener passwordChangeListener) {
        this.passwordChangeListener = passwordChangeListener;
    }

    @Override
    public void onChangeSuccess() {
        passwordChangeListener.onChangeSuccess();
    }

    @Override
    public void onChangeFailure() {
        passwordChangeListener.onChangeFailure();
    }
}

