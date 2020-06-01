package com.itesm.healthchain.data.session;

import com.itesm.healthchain.data.model.LoggedInUser;

public interface LoginStateListener {
    void onLoginSuccess(LoggedInUser user);
    void onLoginFailure();
}
