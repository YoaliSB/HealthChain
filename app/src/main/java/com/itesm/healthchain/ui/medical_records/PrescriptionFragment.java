package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PrescriptionAdapter;
import com.itesm.healthchain.models.Prescription;
import com.itesm.healthchain.models.PrescriptionItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PrescriptionFragment extends Fragment {

    private ExpandableListView expandableListView;
    private PrescriptionAdapter expandableListAdapter;
    private List<Prescription> listDataHeader;
    private HashMap<Prescription, List<PrescriptionItem>> listHashMap;
    private View emptyView;
    PrescriptionViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_prescriptions, container, false);
        getActivity().setTitle(R.string.label_prescriptions);

        emptyView = rootView.findViewById(R.id.empty);
        expandableListView = rootView.findViewById(R.id.prescription_list);
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();
        viewModel = ViewModelProviders.of(getActivity()).get(PrescriptionViewModel.class);
        viewModel.getPrescriptionMutableLiveData().observe(getActivity(), prescriptionListUpdateObserver);

        return rootView;
    }


    Observer<ArrayList<Prescription>> prescriptionListUpdateObserver =
        new Observer<ArrayList<Prescription>>() {
            @Override
            public void onChanged(ArrayList<Prescription> prescriptionArrayList) {

                for (Prescription prescription : prescriptionArrayList) {
                    listDataHeader.add(prescription);
                    listHashMap.put(prescription, prescription.getItems());
                }

                if (listDataHeader.size() <= 0) {
                    expandableListView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                }
                expandableListAdapter = new PrescriptionAdapter(getContext(), listDataHeader, listHashMap);
                expandableListView.setAdapter(expandableListAdapter);
            }
        };
}
