package com.itesm.healthchain.ui.settings;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.session.PasswordChangeStateListener;
import com.itesm.healthchain.data.session.UserRepository;

public class AccountSettingsFragment extends Fragment implements PasswordChangeStateListener {

    private AccountSettingsViewModel mViewModel;

    public static AccountSettingsFragment newInstance() {
        return new AccountSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.account_settings_fragment, container, false);
        Button saveBtn = root.findViewById(R.id.save_btn);
        final TextView name = root.findViewById(R.id.text_name);
        name.setText(SharedPreferencesManager.getName(getActivity()));
        final TextView oldPassword = root.findViewById(R.id.currentPassword);
        final TextView newPassword = root.findViewById(R.id.newPassword);
        final TextView confNewPassword = root.findViewById(R.id.confNewPassword);
        final UserRepository repository = new UserRepository(getActivity());
        repository.setPasswordListener(this);

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String oldPss= oldPassword.getText().toString().trim();
                final String newPss= newPassword.getText().toString().trim();
                final String confPss= confNewPassword.getText().toString().trim();
                if(TextUtils.isEmpty(newPss) || newPss.length() < 6)
                {
                    newPassword.setError("Tu contraseña debe ser de al menos 6 caracteres");
                    return;
                }
                if(TextUtils.isEmpty(confPss) || !newPss.equals(confPss))
                {
                    confNewPassword.setError("Tu contraseña debe coincidir con la de arriba");
                    return;
                }
                repository.updatePassword(oldPss, newPss);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AccountSettingsViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onChangeSuccess() {
        Toast.makeText(getActivity(), getString(R.string.password_changeSuccess),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChangeFailure() {
        Toast.makeText(getActivity(), getString(R.string.password_changeFail),
                Toast.LENGTH_LONG).show();
    }
}
