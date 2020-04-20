package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.itesm.healthchain.R;
import com.itesm.healthchain.adapters.PrescriptionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PrescriptionFragment extends Fragment {

    private ExpandableListView expandableListView;
    private PrescriptionAdapter expandableListAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_prescriptions, container,false);
        getActivity().setTitle(R.string.label_prescriptions);

        emptyView = rootView.findViewById(R.id.empty);
        expandableListView = (ExpandableListView)rootView.findViewById(R.id.prescription_list);
        initData();
        return rootView;
    }

    public void initData(){
        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>()
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot faqSnapshot : dataSnapshot.getChildren()){
                    Faq faq = faqSnapshot.getValue(Faq.class);
                    listDataHeader.add(faq.getQuestion());
                    listHashMap.put(listDataHeader
                            .get(Integer.parseInt(faq.getId())),new ArrayList<String>());
                    listHashMap.get(listDataHeader
                            .get(Integer.parseInt(faq.getId()))).add(faq.getAnswer());
                }

                if(listDataHeader.size()<=0){
                    expandableListView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    expandableListView.setVisibility(View.VISIBLE);
                }

                expandableListAdapter = new PrescriptionAdapter(getActivity(), listDataHeader, listHashMap);
                expandableListView.setAdapter(expandableListAdapter);

            }
        };
        myRef.addValueEventListener(eventListener);

    }
}
