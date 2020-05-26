package com.itesm.healthchain.ui.personal_data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.healthchain.R;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.personal.EditPersonalDataListener;
import com.itesm.healthchain.data.personal.PersonalDataRepository;
import com.itesm.healthchain.nfc.NfcActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PersonalEditFragment extends Fragment implements EditPersonalDataListener {

    private PatientDataViewModel patientDataViewModel;
    private PersonalDataRepository personalDataRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        patientDataViewModel =
                ViewModelProviders.of(this,
                        new PatientDataViewModel.Factory(getActivity()))
                        .get(PatientDataViewModel.class);
        personalDataRepository = PersonalDataRepository.getInstance(getContext());
        personalDataRepository.setEditPersonalDataListener(this);
        View root = inflater.inflate(R.layout.personal_edit_fragment, container, false);

        final TextView name = root.findViewById(R.id.text_name);
        final TextView age = root.findViewById(R.id.text_age);
        final TextView blood = root.findViewById(R.id.text_blood);
        final TextView weight = root.findViewById(R.id.text_weight);
        final TextView height = root.findViewById(R.id.text_height);
        final TextView contactName = root.findViewById(R.id.text_contact_name);
        final TextView contactPhone = root.findViewById(R.id.text_contact_phone);
        final TextView contactRelationship = root.findViewById(R.id.text_contact_relationship);
        final TextView hospital = root.findViewById(R.id.text_hospital);
        final TextView ailments = root.findViewById(R.id.list_ailments);
        final TextView allergies = root.findViewById(R.id.list_allergies);
        final Button saveButton = root.findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalData data = patientDataViewModel.getData().getValue();
                data.setContactName(contactName.getText().toString());
                data.setContactPhone(contactPhone.getText().toString());
                data.setContactRelationship(contactRelationship.getText().toString());
                data.setHospital(hospital.getText().toString());
                personalDataRepository.updatePersonalData(data);
            }
        });

        patientDataViewModel.getData().observe(getViewLifecycleOwner(), new Observer<PersonalData>() {
            @Override
            public void onChanged(@Nullable PersonalData data) {
                    name.setText(data.getName());
                    age.setText(data.getBirthDate());
                    blood.setText(data.getBlood());
                    weight.setText(data.getWeight());
                    height.setText(data.getHeight());
                    contactName.setText(data.getContactName());
                    contactPhone.setText(data.getContactPhone());
                    contactRelationship.setText(data.getContactRelationship());
                    hospital.setText(data.getHospital());
                    ailments.setText(data.getAilments());
                    allergies.setText(data.getAllergies());
            }
        });
        return root;
    }

    @Override
    public void onEditFailure() {
        Toast.makeText(getContext(), getString(R.string.personal_edit_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEditSuccess(PersonalData data) {
        NfcActivity activity = (NfcActivity) getActivity();
        activity.tagProfile.setContactName(data.getContactName());
        activity.tagProfile.setContactPhone(data.getContactPhone());
        activity.tagProfile.setContactRelationship(data.getContactRelationship());
        activity.tagProfile.setHospital(data.getHospital());
        activity.confirmTagWrite();
    }
}
