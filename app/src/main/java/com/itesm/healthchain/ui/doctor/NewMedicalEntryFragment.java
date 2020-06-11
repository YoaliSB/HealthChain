package com.itesm.healthchain.ui.doctor;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PrescriptionItemAdapter;
import com.itesm.healthchain.data.SharedPreferencesManager;
import com.itesm.healthchain.data.model.MedicalRecordEntry;
import com.itesm.healthchain.data.model.PersonalData;
import com.itesm.healthchain.data.model.Prescription;
import com.itesm.healthchain.data.model.PrescriptionItem;
import com.itesm.healthchain.data.personal.EditMedicalRecordListener;
import com.itesm.healthchain.data.personal.PatientDataRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewMedicalEntryFragment extends Fragment implements EditMedicalRecordListener {
    private PrescriptionItemAdapter prescriptionItemAdapter;
    private DoctorPatientDataViewModel patientDataViewModel;

    TextView doctor;
    TextView name;
    TextView sex;
    TextView ta;
    TextView fc;
    TextView fr;
    TextView temp;
    TextView weight;
    TextView height;
    TextView imc;
    TextView observations;
    TextView diagnosis;
    RecyclerView prescriptionsList;
    Button addPrescriptionButton;
    EditText prescriptionNameView;
    EditText prescriptionDoseView;
    Button datePickerOpener;
    MaterialDatePicker<Long> datePicker;
    Button saveButton;

    List<PrescriptionItem> prescriptionItemList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.new_medical_entry_fragment, container, false);

        patientDataViewModel =
                ViewModelProviders.of(getActivity(),
                        new DoctorPatientDataViewModel.Factory(getActivity()))
                        .get(DoctorPatientDataViewModel.class);

        PatientDataRepository.getInstance(getActivity()).setEditMedicalRecordListener(this);

        doctor = root.findViewById(R.id.text_doctor);
        name = root.findViewById(R.id.text_name);
        sex = root.findViewById(R.id.text_sex);
        ta = root.findViewById(R.id.text_ta);
        fc = root.findViewById(R.id.text_fc);
        fr = root.findViewById(R.id.text_fr);
        temp = root.findViewById(R.id.text_temp);
        weight = root.findViewById(R.id.text_weight);
        height = root.findViewById(R.id.text_height);
        imc = root.findViewById(R.id.text_bmi);
        observations = root.findViewById(R.id.text_observations);
        diagnosis = root.findViewById(R.id.text_diagnosis);
        prescriptionsList = root.findViewById(R.id.list_prescriptions);
        addPrescriptionButton = root.findViewById(R.id.add_prescription);
        prescriptionNameView = root.findViewById(R.id.prescription_name);
        prescriptionDoseView = root.findViewById(R.id.prescription_dose);
        datePickerOpener = root.findViewById(R.id.prescription_date_button);
        datePicker = MaterialDatePicker.Builder.datePicker().build();
        saveButton = root.findViewById(R.id.save_btn);

        final Context context = getContext();

        prescriptionItemAdapter = new PrescriptionItemAdapter();
        prescriptionItemAdapter.setPrescriptionItems(new ArrayList<PrescriptionItem>());
        prescriptionsList.setAdapter(prescriptionItemAdapter);
        prescriptionsList.setLayoutManager(new LinearLayoutManager(getContext()));

        patientDataViewModel.getData().observe(getViewLifecycleOwner(), new Observer<PersonalData>() {
            @Override
            public void onChanged(@Nullable PersonalData data) {
                MedicalRecordEntry entry = MedicalRecordEntry.prefillEntry(data, SharedPreferencesManager.getName(context));
                name.setText(entry.getName());
                doctor.setText(entry.getDoctor());
                temp.setText(entry.getTemp());
                weight.setText(entry.getWeight());
                height.setText(entry.getHeight());
                imc.setText(entry.getImc());
            }
        });

        //TESTING
//        sex.setText("M");
//        ta.setText("120/80");
//        fc.setText("80");
//        fr.setText("15");
//        observations.setText("Escurrimiento nasal");
//        diagnosis.setText("Gripe común");
//        prescriptionNameView.setText("Antiviral");
//        prescriptionDoseView.setText("1 cap c/ 8 hrs");


        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
                 public void onPositiveButtonClick(Long selection) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                    Date date = new Date(selection);
                    datePickerOpener.setText(sdf.format(date));
                  }
        });

        datePickerOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        addPrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prescriptionName = prescriptionNameView.getText().toString();
                String prescriptionDose = prescriptionDoseView.getText().toString();
                String prescriptionDate = datePickerOpener.getText().toString();

                if(!prescriptionName.isEmpty() && !prescriptionDate.isEmpty() && !prescriptionDate.isEmpty()) {
                    PrescriptionItem item = new PrescriptionItem(prescriptionName, prescriptionDose, prescriptionDate);
                    prescriptionItemList.add(item);
                    prescriptionItemAdapter.addItem(item);
                    prescriptionNameView.setText(null);
                    prescriptionDoseView.setText(null);
                    datePickerOpener.setText(null);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientDataViewModel.updateMedicalRecord(getNewEntry());
            }
        });

        return root;
    }

    private MedicalRecordEntry getNewEntry() {
        String strName = name.getText().toString();
        String strDoctor = doctor.getText().toString();
        String strDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String strSex = sex.getText().toString();
        String strTa = ta.getText().toString();
        String strFc = fc.getText().toString();
        String strFr = fr.getText().toString();
        String strTemp = temp.getText().toString();
        String strWeight = weight.getText().toString();
        String strHeight = height.getText().toString();
        String strObservations = observations.getText().toString();
        String strDiagnosis = diagnosis.getText().toString();
        Prescription newPrescription = new Prescription(strDoctor, strDate, prescriptionItemList);
        return new MedicalRecordEntry(strName, strDoctor, strDate, strSex, strTa, strFc, strFr,
                Double.parseDouble(strTemp), Double.parseDouble(strWeight),
                Double.parseDouble(strHeight), strObservations, strDiagnosis, newPrescription);
    }

    @Override
    public void onEditRecordFailure() {
        Toast.makeText(getContext(), getString(R.string.error_update_record), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEditRecordSuccess(String email) {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        NavHostFragment.findNavController(this).navigate(R.id.navigation_patient_detail_menu, bundle);
    }
}
