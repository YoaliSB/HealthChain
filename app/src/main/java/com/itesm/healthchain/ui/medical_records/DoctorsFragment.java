package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.DoctorAdapter;
import com.itesm.healthchain.data.DoctorDeleteListener;
import com.itesm.healthchain.data.DoctorRepository;
import com.itesm.healthchain.data.model.Doctor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorsFragment extends Fragment implements DoctorDeleteListener {

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private ArrayList<Doctor> doctors = new ArrayList<>();
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
        DoctorRepository doctorRepository = DoctorRepository.getInstance(getActivity());
        doctorRepository.setListener(this);
        viewModel =
                ViewModelProviders.of(this,
                        new DoctorViewModel.Factory(doctorRepository))
                        .get(DoctorViewModel.class);
        viewModel.getDummyData().observe(getActivity(), doctorListUpdateObserver);
        doctorAdapter = new DoctorAdapter(doctors);
        doctorAdapter.setDeleteListener(this);
        recyclerView.setAdapter(doctorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    Observer<ArrayList<Doctor>> doctorListUpdateObserver =
        new Observer<ArrayList<Doctor>>() {
            @Override
            public void onChanged(ArrayList<Doctor> doctorArrayList) {
                if (doctorArrayList.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    // Remove inactive doctors
                    Iterator<Doctor> itr = doctorArrayList.iterator();
                    while (itr.hasNext()) {
                        Doctor dr = itr.next();
                        if (!dr.isActive()) {
                            itr.remove();
                        }
                    }

                    doctorAdapter.addItems(doctorArrayList);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        };

    @Override
    public void onDelete(Doctor deletedDoctor) {
        viewModel.deleteDoctor(deletedDoctor);
    }

    @Override
    public void onFailure() {
        Toast.makeText(getContext(),getString(R.string.delete_doctor_fail), Toast.LENGTH_LONG).show();
    }
}
