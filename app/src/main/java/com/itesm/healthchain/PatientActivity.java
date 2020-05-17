package com.itesm.healthchain;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.itesm.healthchain.data.PersonalDataRepository;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.TagProfile;
import com.itesm.healthchain.nfc.NfcActivity;
import com.itesm.healthchain.ui.personal_data.PatientPersonalDataViewModel;
import com.itesm.healthchain.ui.personal_data.PersonalDataViewModel;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class PatientActivity extends NfcActivity {

    private PersonalDataViewModel personalDataViewModel;
    private NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        PersonalDataRepository personalDataRepository = PersonalDataRepository.getInstance(this);
        personalDataViewModel =
                ViewModelProviders.of(this, new PatientPersonalDataViewModel.Factory(personalDataRepository)).get(PatientPersonalDataViewModel.class);
    }

    @Override
    protected void onNfcWriteSuccess() {
        PersonalData data = personalDataViewModel.getData().getValue();
        String name = tagProfile.getContactName();
        String phone = tagProfile.getContactPhone();
        String relationship = tagProfile.getContactRelationship();
        String preferredHospital = tagProfile.getHospital();
        data.setContactName(name);
        data.setContactPhone(phone);
        data.setContactRelationship(relationship);
        data.setHospital(preferredHospital);
        personalDataViewModel.setData(data);
        navController.navigate(R.id.navigation_personal);
    }

}
