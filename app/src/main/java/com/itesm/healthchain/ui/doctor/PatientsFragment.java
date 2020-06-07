package com.itesm.healthchain.ui.doctor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PatientAdapter;
import com.itesm.healthchain.data.model.PatientInfo;
import com.itesm.healthchain.data.patients.PatientRepository;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PatientsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PatientAdapter patientAdapter;
    private ArrayList<PatientInfo> patients = new ArrayList<>();
    private View emptyView;
    PatientsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container, false);
        getActivity().setTitle(R.string.title_patients);

        emptyView = rootView.findViewById(R.id.empty);
        TextView emptyText = emptyView.findViewById(R.id.text);
        emptyText.setText(R.string.empty_patients);
        recyclerView = rootView.findViewById(R.id.list);
        PatientRepository patientRepository = PatientRepository.getInstance(getActivity());
        patientAdapter = new PatientAdapter(patients);
        viewModel =
                ViewModelProviders.of(this,
                        new PatientsViewModel.Factory(patientRepository))
                        .get(PatientsViewModel.class);
        viewModel.getDummyData().observe(getActivity(), patientListUpdateObserver);
        recyclerView.setAdapter(patientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    Observer<ArrayList<PatientInfo>> patientListUpdateObserver =
            new Observer<ArrayList<PatientInfo>>() {
                @Override
                public void onChanged(ArrayList<PatientInfo> patientArrayList) {
                    if (patientArrayList.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                    patientAdapter.addItems(patientArrayList);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            };
}
