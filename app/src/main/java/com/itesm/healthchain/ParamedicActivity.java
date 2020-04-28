package com.itesm.healthchain;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itesm.healthchain.models.PersonalData;
import com.itesm.healthchain.models.TagProfile;
import com.itesm.healthchain.nfc.NfcActivity;
import com.itesm.healthchain.ui.personal_data.PersonalDataViewModel;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class ParamedicActivity extends NfcActivity {
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Bottom nav
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.inflateMenu(R.menu.paramedic_nav_menu);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_personal, R.id.navigation_settings)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // Testing
//        PersonalDataViewModel personalDataViewModel =
//                ViewModelProviders.of(this).get(PersonalDataViewModel.class);
//        personalDataViewModel.setData(new PersonalData(new TagProfile()));

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        PersonalDataViewModel personalDataViewModel =
                ViewModelProviders.of(this).get(PersonalDataViewModel.class);
        personalDataViewModel.setData(new PersonalData(super.tagProfile));
        navController.navigate(R.id.navigation_personal);
    }
}
