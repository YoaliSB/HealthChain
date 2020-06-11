package com.itesm.healthchain.ui.settings;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itesm.healthchain.EmailActivity;
import com.itesm.healthchain.R;
import com.itesm.healthchain.data.session.LogoutStateListener;
import com.itesm.healthchain.data.session.UserRepository;

import java.util.List;
import java.util.Stack;

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
//
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("*/*");
                i.putExtra(Intent.EXTRA_EMAIL, new String[] {
                        "info@healthchain.com"
                });
                i.putExtra(Intent.EXTRA_SUBJECT, "HealthChain App: Dudas o comentarios");

                startActivity(createEmailOnlyChooserIntent(i, "Elige una app para mandar el correo."));
                break;
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

    public Intent createEmailOnlyChooserIntent(Intent source,
                                               CharSequence chooserTitle) {
        Stack<Intent> intents = new Stack<Intent>();
        Intent i = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",
                "info@healthchain.com", null));
        List<ResolveInfo> activities = getActivity().getPackageManager()
                .queryIntentActivities(i, 0);

        for(ResolveInfo ri : activities) {
            Intent target = new Intent(source);
            target.setPackage(ri.activityInfo.packageName);
            intents.add(target);
        }

        if(!intents.isEmpty()) {
            Intent chooserIntent = Intent.createChooser(intents.remove(0),
                    chooserTitle);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                    intents.toArray(new Parcelable[intents.size()]));

            return chooserIntent;
        } else {
            return Intent.createChooser(source, chooserTitle);
        }
    }

}
