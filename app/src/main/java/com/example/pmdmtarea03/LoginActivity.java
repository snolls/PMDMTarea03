package com.example.pmdmtarea03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

/**
 * Actividad de inicio de sesión para autenticar usuarios con Firebase Authentication.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inicia el flujo de inicio de sesión utilizando FirebaseUI.
     */
    private void startSignIn() {
        // Configurar los proveedores de autenticación (Email y Google)
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(), // Inicio de sesión con correo electrónico
                new AuthUI.IdpConfig.GoogleBuilder().build() // Inicio de sesión con Google
        );

        // Crear la intención para iniciar el flujo de inicio de sesión
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers) // Establecer los proveedores disponibles
                .setLogo(R.drawable.logo_pokemon) // Configurar el logo que se mostrará en la pantalla de inicio de sesión
                .setTheme(R.style.Theme_PMDMTarea03) // Configurar el tema visual
                .build();

        // Lanzar el flujo de inicio de sesión
        signInLauncher.launch(signInIntent);
    }

    /**
     * Launcher para manejar el resultado del flujo de inicio de sesión.
     */
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    // Manejar el resultado del inicio de sesión
                    onSignInResult(result);
                }
            }
    );

    /**
     * Maneja el resultado del flujo de inicio de sesión.
     *
     * @param result El resultado del flujo de autenticación.
     */
    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse(); // Obtener la respuesta del flujo
        if (result.getResultCode() == RESULT_OK) {
            // Inicio de sesión exitoso
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Obtener el usuario autenticado
            goToMainActivity(); // Navegar a la actividad principal
        } else {
            // Inicio de sesión fallido
            Toast.makeText(this, "Error al conectar", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que se ejecuta al iniciar la actividad.
     * Verifica si el usuario ya está autenticado.
     */
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // Verificar si hay un usuario autenticado
        if (user != null) {
            // Si hay un usuario autenticado, navegar directamente a la actividad principal
            goToMainActivity();
        } else {
            // Si no hay un usuario autenticado, iniciar el flujo de inicio de sesión
            startSignIn();
        }
    }

    /**
     * Navega a la actividad principal de la aplicación.
     */
    private void goToMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i); // Iniciar la actividad principal
        finish(); // Finalizar la actividad de inicio de sesión para que no quede en el historial
    }
}
