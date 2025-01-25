package com.example.pmdmtarea03;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.pmdmtarea03.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

/**
 * Actividad principal de la aplicación que gestiona la navegación entre fragmentos
 * y aplica las configuraciones de idioma.
 */
public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "AppPrefs"; // Nombre del archivo de SharedPreferences
    private static final String LANGUAGE_KEY = "language"; // Clave para guardar el idioma en SharedPreferences

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Cargar idioma desde SharedPreferences antes de establecer el diseño
        loadLanguagePreference();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar la barra de navegación inferior (BottomNavigationView)
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);

        // Establecer el fragmento inicial cuando la actividad se crea por primera vez
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, new CapturedFragment()) // Fragmento inicial
                    .commit();
        }

        // Manejar la selección de elementos en la barra de navegación inferior
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            // Determinar qué fragmento mostrar según el elemento seleccionado
            if (item.getItemId() == R.id.navigation_pokedex) {
                selectedFragment = new PokedexFragment(); // Fragmento de la Pokédex
            } else if (item.getItemId() == R.id.navigation_capturados) {
                selectedFragment = new CapturedFragment(); // Fragmento de Pokémon capturados
            } else if (item.getItemId() == R.id.navigation_tools) {
                selectedFragment = new ToolsFragment(); // Fragmento de herramientas
            }

            // Reemplazar el fragmento actual con el seleccionado
            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, selectedFragment)
                        .commit();
            }
            return true; // Indicar que el evento fue manejado correctamente
        });
    }

    /**
     * Carga el idioma almacenado en SharedPreferences antes de inicializar la actividad.
     */
    private void loadLanguagePreference() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String languageCode = prefs.getString(LANGUAGE_KEY, "es"); // Cargar idioma, por defecto español ("es")
        setLocale(languageCode);
    }

    /**
     * Configura la aplicación para usar el idioma seleccionado.
     *
     * @param languageCode El código del idioma que se debe usar (por ejemplo, "es" para español, "en" para inglés).
     */
    private void setLocale(String languageCode) {
        // Crear un objeto Locale con el idioma seleccionado
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale); // Establecer el idioma por defecto

        // Configurar el idioma en la configuración del sistema
        Configuration config = new Configuration();
        config.setLocale(locale);

        // Aplicar la configuración de idioma a los recursos de la aplicación
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}
