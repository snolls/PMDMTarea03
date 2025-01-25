package com.example.pmdmtarea03;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

/**
 * Fragmento de herramientas (ToolsFragment) que permite al usuario cambiar el idioma,
 * configurar opciones de eliminación de Pokémon capturados, y cerrar sesión.
 */
public class ToolsFragment extends Fragment {

    private FragmentToolsBinding binding; // Variable de View Binding para acceder a las vistas
    private FirebaseAuth mAuth; // Autenticación con Firebase
    private static final String PREFS_NAME = "AppPrefs"; // Nombre de las SharedPreferences
    private static final String LANGUAGE_KEY = "language"; // Clave para almacenar el idioma seleccionado
    private static final String BORRADO_KEY = "borrar"; // Clave para almacenar el estado del switch de eliminación

    public ToolsFragment() {
        // Constructor público requerido
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance(); // Inicializar Firebase Auth
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inicializar View Binding para acceder a las vistas definidas en el layout
        binding = FragmentToolsBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Retornar la vista raíz del fragmento
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener las preferencias compartidas
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String languageCode = prefs.getString(LANGUAGE_KEY, "es"); // Español como idioma predeterminado

        // Configurar el estado inicial del switch de idioma
        binding.switchLang.setChecked("en".equals(languageCode));

        // Configurar el estado inicial del switch de eliminación
        boolean isDeleteEnabled = prefs.getBoolean(BORRADO_KEY, false); // Falso por defecto
        binding.switchDeletePokemon.setChecked(isDeleteEnabled);

        // Listener para cambiar el idioma al activar/desactivar el switch
        binding.switchLang.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                changeLanguage("en"); // Cambiar a inglés
            } else {
                changeLanguage("es"); // Cambiar a español
            }
        });

        // Listener para cambiar el estado de eliminación al activar/desactivar el switch
        binding.switchDeletePokemon.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(BORRADO_KEY, isChecked); // Guardar el estado del switch
            editor.apply();
        });

        // Configurar el botón de "Cerrar sesión"
        binding.logoutLayout.setOnClickListener(v -> {
            mAuth.signOut(); // Cerrar sesión en Firebase
            Toast.makeText(getActivity(), getString(R.string.sesion_closed), Toast.LENGTH_SHORT).show();

            // Redirigir al usuario a la pantalla de inicio de sesión
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Limpiar el historial de actividades
            startActivity(intent);
        });

        // Configurar el botón de "Acerca de"
        binding.aboutLayout.setOnClickListener(v -> {
            String title = getString(R.string.about_title); // Título del diálogo
            String message = getString(R.string.about_message); // Mensaje del diálogo
            String positiveButtonText = getString(R.string.accept); // Texto del botón positivo

            // Mostrar un diálogo de información
            new AlertDialog.Builder(requireContext())
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveButtonText, (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    /**
     * Cambia el idioma de la aplicación y reinicia la actividad para aplicar los cambios.
     *
     * @param languageCode Código de idioma a aplicar (por ejemplo, "es" para español, "en" para inglés).
     */
    private void changeLanguage(String languageCode) {
        // Guardar el idioma seleccionado en SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(LANGUAGE_KEY, languageCode);
        editor.apply();

        // Cambiar la configuración de idioma
        setLocale(languageCode);

        // Reiniciar la actividad para aplicar el idioma
        requireActivity().recreate();
    }

    /**
     * Aplica la configuración de idioma a los recursos de la aplicación.
     *
     * @param languageCode Código de idioma a aplicar.
     */
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode); // Crear un objeto Locale con el idioma seleccionado
        Locale.setDefault(locale); // Establecer el idioma por defecto
        Configuration config = new Configuration();
        config.setLocale(locale); // Configurar el idioma en la configuración del sistema
        requireActivity().getResources().updateConfiguration(config, requireActivity().getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Liberar el binding para evitar fugas de memoria
        binding = null;
    }
}
