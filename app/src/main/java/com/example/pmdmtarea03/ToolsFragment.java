package com.example.pmdmtarea03;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;

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
    private static final String PREFS_NAME = "AppPrefs";
    private static final String LANGUAGE_KEY = "language";
    private static final String BORRADO_KEY = "borrar";

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

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String languageCode = prefs.getString(LANGUAGE_KEY, "es"); // Español por defecto

        // Configurar el estado inicial del switch de idioma
        binding.switchLang.setChecked("en".equals(languageCode));

        // Configurar el estado inicial del switch de eliminación
        boolean isDeleteEnabled = prefs.getBoolean(BORRADO_KEY, false); // Estado predeterminado: false
        binding.switchDeletePokemon.setChecked(isDeleteEnabled);

        // Listener para el Switch de idioma
        binding.switchLang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeLanguage("en");
            } else {
                changeLanguage("es");
            }
        });

        // Listener para el Switch de eliminación
        binding.switchDeletePokemon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean(BORRADO_KEY, isChecked).apply();
        });

        // Configurar el clic en "Cerrar sesión"
        binding.logoutLayout.setOnClickListener(v -> {
            mAuth.signOut(); // Cierra sesión en Firebase
            Toast.makeText(getActivity(), "Sesión cerrada", Toast.LENGTH_SHORT).show();

            // Redirigir al usuario a la pantalla de inicio de sesión
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Configurar el clic en "Acerca de"
        binding.aboutLayout.setOnClickListener(v -> {
            String title = getString(R.string.about_title);
            String message = getString(R.string.about_message);
            String positiveButtonText = getString(R.string.accept);

            new AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonText, (dialog, which) -> dialog.dismiss())
                    .show();
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