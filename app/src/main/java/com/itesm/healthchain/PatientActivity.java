package com.itesm.healthchain;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itesm.healthchain.models.PersonalData;
import com.itesm.healthchain.models.TagProfile;
import com.itesm.healthchain.ui.personal_data.PersonalDataViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PatientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Bottom nav
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.inflateMenu(R.menu.patient_nav_menu);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // Don't require back button
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_personal, R.id.navigation_records_main, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//
//        PersonalDataViewModel personalDataViewModel =
//                ViewModelProviders.of(this).get(PersonalDataViewModel.class);
//        personalDataViewModel.setData(new PersonalData(new TagProfile()));
    }

}
