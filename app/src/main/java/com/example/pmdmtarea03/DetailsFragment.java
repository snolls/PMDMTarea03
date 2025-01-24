package com.example.pmdmtarea03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.example.pmdmtarea03.databinding.FragmentDetailsBinding;
import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;


public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar View Binding
        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        // Retornar la vista raíz del binding
        return binding.getRoot();
    }
}