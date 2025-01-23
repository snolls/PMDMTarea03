package com.example.pmdmtarea03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.example.pmdmtarea03.databinding.FragmentPokedexBinding;
import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;


public class PokedexFragment extends Fragment {

    private FragmentPokedexBinding binding;
    private FirebaseAuth mAuth;

    public PokedexFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); // Inicializar Firebase Auth

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar View Binding
        binding = FragmentPokedexBinding.inflate(inflater, container, false);

        // Retornar la vista raíz del binding
        return binding.getRoot();
    }
}