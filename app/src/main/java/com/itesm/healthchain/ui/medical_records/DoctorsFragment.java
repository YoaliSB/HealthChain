package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PersonAdapter;
import com.itesm.healthchain.data.model.Doctor;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private PersonAdapter personAdapter;
    private ArrayList<Doctor> doctors = new ArrayList<>();;
    private View emptyView;
    DoctorViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container, false);
        getActivity().setTitle(R.string.title_doctors);

        emptyView = rootView.findViewById(R.id.empty);
        TextView emptyText = emptyView.findViewById(R.id.text);
        emptyText.setText(R.string.empty_doctors);
        recyclerView = rootView.findViewById(R.id.list);
        viewModel =
                ViewModelProviders.of(this,
                        new DoctorViewModel.Factory(getActivity()))
                        .get(DoctorViewModel.class);
        viewModel.getData().observe(getActivity(), peopleListUpdateObserver);
        personAdapter = new PersonAdapter(doctors);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    Observer<ArrayList<Doctor>> peopleListUpdateObserver =
        new Observer<ArrayList<Doctor>>() {
            @Override
            public void onChanged(ArrayList<Doctor> doctorArrayList) {
                if (doctorArrayList.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    doctors.addAll(doctorArrayList);
                    personAdapter = new PersonAdapter(doctors);
                    recyclerView.setAdapter(personAdapter);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        };
}
