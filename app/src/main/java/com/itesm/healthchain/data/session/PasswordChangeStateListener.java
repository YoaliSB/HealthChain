package com.itesm.healthchain.data.session;

public interface PasswordChangeStateListener {
    void onChangeSuccess();
    void onChangeFailure();
}
