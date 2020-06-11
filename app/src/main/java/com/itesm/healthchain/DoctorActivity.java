package com.itesm.healthchain;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.nfc.NfcActivity;
import com.itesm.healthchain.ui.doctor.DoctorPatientDataViewModel;
import com.itesm.healthchain.ui.personal_data.PatientDataViewModel;
import com.itesm.healthchain.ui.personal_data.PersonalDataViewModel;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class DoctorActivity extends NfcActivity {
    private NavController navController;
    PersonalDataViewModel personalDataViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Bottom nav
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.inflateMenu(R.menu.doctor_nav_menu);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_settings)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        navController.setGraph(R.navigation.doctor_navigation);

        personalDataViewModel =
                ViewModelProviders.of(this, new PatientDataViewModel.Factory(this))
                        .get(PatientDataViewModel.class);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        DoctorPatientDataViewModel personalDataViewModel =
//                ViewModelProviders.of(this,
//                        new DoctorPatientDataViewModel.Factory(this))
//                        .get(DoctorPatientDataViewModel.class);
//        personalDataViewModel.getData().postValue(new PersonalData(super.tagProfile));
        Bundle bundle = new Bundle();
        bundle.putString("email", tagProfile.getEmail());
        navController.navigate(R.id.navigation_patient_detail_menu, bundle);
    }
}