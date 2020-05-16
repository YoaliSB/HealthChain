package com.itesm.healthchain.data;

import android.content.Context;

import com.itesm.healthchain.LoginActivity;
import com.itesm.healthchain.data.model.LoggedInUser;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;
    private LoginStateListener loginListener;

    // private constructor : singleton access
    private UserRepository(LoginDataSource dataSource, LifecycleOwner owner) {
        this.dataSource = dataSource;
        dataSource.subscribe().observe(owner, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(@Nullable LoggedInUser loggedInUser) {
                if (loggedInUser != null) {
                    setLoggedInUser(loggedInUser);
                } else {
                    loginListener.onLoginFailure();
                }
            }
        });
    }

    public static UserRepository getInstance(LoginDataSource dataSource, LifecycleOwner owner) {
        if (instance == null) {
            instance = new UserRepository(dataSource, owner);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        // TODO: Create logout datasource
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        loginListener.onLoginSuccess(user);
    }

    public void login(String username, String password, Context context) {
        dataSource.login(username, password, context);
    }

    public LoggedInUser getUser() {
        return user;
    }

    public void setLoginListener(LoginActivity loginActivity) {
        this.loginListener = loginActivity;
    }

    public interface LoginStateListener {
        void onLoginSuccess(LoggedInUser user);
        void onLoginFailure();
    }
}
