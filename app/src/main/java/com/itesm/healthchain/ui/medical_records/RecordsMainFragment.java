package com.itesm.healthchain.ui.medical_records;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itesm.healthchain.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class RecordsMainFragment extends Fragment {

    private RecordsMainViewModel mainViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mainViewModel =
                ViewModelProviders.of(this).get(RecordsMainViewModel.class);
        View root = inflater.inflate(R.layout.fragment_main_records, container, false);
        return root;
    }
}

