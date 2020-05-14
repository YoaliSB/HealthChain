package com.itesm.healthchain.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.settings_fragment, container, false);
        View aboutBtn = root.findViewById(R.id.btn_info);
        View editBtn = root.findViewById(R.id.btn_edit_profile);
        aboutBtn.setOnClickListener(this);
        editBtn.setOnClickListener(this);
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
        }
    }

    private void replaceFragment(int id) {
        NavHostFragment.findNavController(this).navigate(id);
    }

}
