package com.itesm.healthchain.data.session;

public interface LogoutStateListener {
    void onLogoutSuccess();
    void onLogoutFailure();
}
