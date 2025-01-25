package com.example.pmdmtarea03;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;


public class ToolsFragment extends Fragment {

    private FragmentToolsBinding binding; // Variable de View Binding
    private FirebaseAuth mAuth;
    private static final String PREFS_NAME = "app_prefs";
    private static final String LANGUAGE_KEY = "language";

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

        // Cargar el idioma actual desde SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String languageCode = prefs.getString(LANGUAGE_KEY, "es"); // Español por defecto

        // Configurar el estado del switch según el idioma
        binding.switchLang.setChecked("en".equals(languageCode));

        // Escuchar cambios en el SwitchCompat
        binding.switchLang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            /**
             * Controla los cambios en el SwitchCompat para cambiar el idioma de la aplicación.
             *
             * @param buttonView El SwitchCompat que se ha pulsado.
             * @param isChecked Indica si el SwitchCompat está activado o desactivado.
             */
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    changeLanguage("en"); // Cambiar a inglés
                } else {
                    changeLanguage("es"); // Cambiar a español
                }
            }
        });

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

    /**
     * Cambia el idioma de la aplicación y reinicia la actividad principal para aplicar los cambios.
     *
     * @param languageCode El código de idioma que se aplicará (por ejemplo, "es" para español, "en" para inglés).
     */
    private void changeLanguage(String languageCode) {
        // Guardar el idioma en SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();

        // Cambiar la configuración de idioma
        setLocale(languageCode);

        // Reiniciar la actividad para aplicar el nuevo idioma
        requireActivity().recreate();
    }

    /**
     * Establece la configuración de idioma para la aplicación.
     *
     * @param languageCode El código de idioma que se aplicará.
     */
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        requireActivity().getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar el binding cuando la vista se destruya
        binding = null;
    }
}