package com.example.pmdmtarea03;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;


public class ToolsFragment extends Fragment {

    private FragmentToolsBinding binding; // Variable de View Binding
    private FirebaseAuth mAuth;

    public ToolsFragment() {
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
        binding = FragmentToolsBinding.inflate(inflater, container, false);

        // Retornar la vista raíz del binding
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configurar el clic en el botón "Cerrar sesión"
        binding.logoutLayout.setOnClickListener((View v) -> {
            mAuth.signOut(); // Cierra sesión en Firebase
            Toast.makeText(getActivity(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redirigir al usuario a la pantalla de inicio de sesión
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar el binding cuando la vista se destruya
        binding = null;
    }
}