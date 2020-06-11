package com.itesm.healthchain.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itesm.healthchain.EmailActivity;
import com.itesm.healthchain.R;
import com.itesm.healthchain.data.session.LogoutStateListener;
import com.itesm.healthchain.data.session.UserRepository;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SettingsFragment extends Fragment implements View.OnClickListener, LogoutStateListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        View aboutBtn = root.findViewById(R.id.btn_info);
        View editBtn = root.findViewById(R.id.btn_edit_profile);
        View logoutBtn = root.findViewById(R.id.btn_logout);
        View quejaBtn = root.findViewById(R.id.btn_email);
        aboutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
        quejaBtn.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_info:
                replaceFragment(R.id.navigation_about);
                break;
            case R.id.btn_edit_profile:
                replaceFragment(R.id.navigation_account);
                break;
            case R.id.btn_email:
                replaceFragment(R.id.navigation_email);
            case R.id.btn_logout:
                UserRepository repository = new UserRepository(getActivity());
                repository.setLogoutListener(this);
                repository.logout();
        }
    }

    private void replaceFragment(int id) {
        NavHostFragment.findNavController(this).navigate(id);
    }

    @Override
    public void onLogoutSuccess() {
        replaceFragment(R.id.navigation_login);
    }

    @Override
    public void onLogoutFailure() {
        Toast.makeText(getActivity(), R.string.logout_error, Toast.LENGTH_LONG).show();
    }
}
