package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PrescriptionAdapter;
import com.itesm.healthchain.data.model.Prescription;

import java.util.ArrayList;
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
    private View emptyView;
    PrescriptionViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.prescriptions_fragment, container, false);
        getActivity().setTitle(R.string.label_prescriptions);

        emptyView = rootView.findViewById(R.id.empty);
        expandableListView = rootView.findViewById(R.id.prescription_list);
        listDataHeader = new ArrayList<>();
        viewModel =
                ViewModelProviders.of(getActivity(),
                        new PrescriptionViewModel.Factory(getActivity()))
                        .get(PrescriptionViewModel.class);
        viewModel = ViewModelProviders.of(getActivity()).get(PrescriptionViewModel.class);
        viewModel.getData().observe(getActivity(), prescriptionListUpdateObserver);

        return rootView;
    }


    Observer<List<Prescription>> prescriptionListUpdateObserver =
        new Observer<List<Prescription>>() {
            @Override
            public void onChanged(List<Prescription> prescriptionArrayList) {
                if(prescriptionArrayList != null)
                    listDataHeader.addAll(prescriptionArrayList);

                if (listDataHeader.size() <= 0) {
                    expandableListView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                    expandableListAdapter = new PrescriptionAdapter(getContext(), listDataHeader);
                    expandableListView.setAdapter(expandableListAdapter);
                    expandableListView.expandGroup(0);
                }

            }
        };
}
