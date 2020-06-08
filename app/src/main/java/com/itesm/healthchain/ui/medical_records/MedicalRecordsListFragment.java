package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.DoctorAdapter;
import com.itesm.healthchain.adapters.MedicalRecordAdapter;
import com.itesm.healthchain.data.model.Doctor;
import com.itesm.healthchain.data.model.MedicalRecordEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MedicalRecordsListFragment extends Fragment implements MedicalRecordEntrySelectListener {

    private RecyclerView recyclerView;
    private MedicalRecordAdapter medicalRecordAdapter;
    private ArrayList<MedicalRecordEntry> entries = new ArrayList<>();;
    private View emptyView;
    private MedicalRecordListViewModel viewModel;
    private MedicalRecordEntrySelectListener listener = this;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.recyclerview_fragment, container, false);
        getActivity().setTitle(R.string.title_medical_records);

        emptyView = rootView.findViewById(R.id.empty);
        TextView emptyText = emptyView.findViewById(R.id.text);
        emptyText.setText(R.string.empty_records);
        recyclerView = rootView.findViewById(R.id.list);
        viewModel =
                ViewModelProviders.of(getActivity(),
                        new MedicalRecordListViewModel.Factory(getActivity()))
                        .get(MedicalRecordListViewModel.class);
        viewModel.getData().observe(getActivity(), recordListUpdateObserver);
        medicalRecordAdapter = new MedicalRecordAdapter(entries);
        medicalRecordAdapter.setSelectListener(listener);
        recyclerView.setAdapter(medicalRecordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return rootView;
    }

    Observer<List<MedicalRecordEntry>> recordListUpdateObserver =
        new Observer<List<MedicalRecordEntry>>() {
            @Override
            public void onChanged(List<MedicalRecordEntry> medicalRecordEntries) {
                if (medicalRecordEntries.size() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    entries.addAll(medicalRecordEntries);
                    medicalRecordAdapter = new MedicalRecordAdapter(entries);
                    medicalRecordAdapter.setSelectListener(listener);
                    recyclerView.setAdapter(medicalRecordAdapter);
                    emptyView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        };

    @Override
    public void onSelect(MedicalRecordEntry selectedEntry) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("medicalRecord", selectedEntry);
        NavHostFragment.findNavController(this)
                .navigate(R.id.navigation_medical_record_detail, bundle);
    }
}
