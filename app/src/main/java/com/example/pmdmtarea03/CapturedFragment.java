package com.example.pmdmtarea03;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra los Pokémon capturados por el usuario en un RecyclerView.
 */
public class CapturedFragment extends Fragment {

    private FragmentCapturedBinding binding; // View Binding para acceder a las vistas del fragmento
    private CapturadosAdapter adapter; // Adaptador para el RecyclerView
    private List<PokemonCaptured> capturedPokemonList = new ArrayList<>(); // Lista de Pokémon capturados

    private static final String PREFS_NAME = "AppPrefs"; // Nombre del archivo SharedPreferences
    private static final String BORRADO_KEY = "borrar"; // Clave para guardar el estado del switch de eliminación

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar el binding
        binding = FragmentCapturedBinding.inflate(inflater, container, false);

        // Configurar el RecyclerView con un layout manager y el adaptador
        binding.recyclerViewCaptured.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CapturadosAdapter(capturedPokemonList, pokemon -> {
            // Manejar el clic en un Pokémon para navegar al fragmento de detalles
            Bundle bundle = new Bundle();
            bundle.putString("name", pokemon.getName());
            bundle.putInt("order", pokemon.getOrder());
            bundle.putInt("height", pokemon.getHeight());
            bundle.putInt("weight", pokemon.getWeight());
            bundle.putString("type", pokemon.getType());
            bundle.putString("image", pokemon.getImage());

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);

            // Reemplazar el fragmento actual con el fragmento de detalles
            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, detailsFragment)
                    .addToBackStack(null) // Agregar a la pila de retroceso
                    .commit();
        });
        binding.recyclerViewCaptured.setAdapter(adapter); // Asignar el adaptador al RecyclerView

        // Configurar el swipe para eliminar elementos
        configureSwipe();

        // Cargar los Pokémon capturados desde Firebase
        fetchCapturedPokemons();

        return binding.getRoot(); // Retornar la vista raíz
    }

    /**
     * Configura el swipe para eliminar elementos del RecyclerView.
     */
    private void configureSwipe() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isSwipeEnabled = sharedPreferences.getBoolean(BORRADO_KEY, false); // Leer el estado del switch de eliminación

        if (isSwipeEnabled) {
            // Configurar el ItemTouchHelper para manejar el swipe
            ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false; // No se implementa arrastrar y soltar
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    // Llamar al método para eliminar el Pokémon en la posición actual
                    int position = viewHolder.getAdapterPosition();
                    deletePokemonFromFirebase(position);
                }
            };

            // Vincular el ItemTouchHelper al RecyclerView
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewCaptured);
        } else {
            // Log de depuración si el swipe está deshabilitado
            Log.d("CapturedFragment", "Swipe deshabilitado");
        }
    }

    /**
     * Elimina un Pokémon capturado de Firebase y de la lista local.
     *
     * @param position Posición del Pokémon en la lista.
     */
    private void deletePokemonFromFirebase(int position) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid(); // Obtener el ID del usuario actual

        PokemonCaptured pokemon = capturedPokemonList.get(position);

        // Buscar y eliminar el Pokémon de Firebase
        db.collection("pokemons")
                .document(uid)
                .collection("userPokemons")
                .whereEqualTo("name", pokemon.getName()) // Filtrar por el nombre del Pokémon
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Eliminar el documento de Firebase
                        queryDocumentSnapshots.getDocuments().get(0).getReference().delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Eliminar el Pokémon de la lista local y notificar al adaptador
                                    capturedPokemonList.remove(position);
                                    adapter.notifyItemRemoved(position);
                                })
                                .addOnFailureListener(e -> {
                                    // Manejar errores y restaurar el elemento en la lista
                                    adapter.notifyItemChanged(position);
                                });
                    } else {
                        adapter.notifyItemChanged(position);
                    }
                })
                .addOnFailureListener(e -> adapter.notifyItemChanged(position));
    }

    /**
     * Carga los Pokémon capturados desde Firebase y actualiza el RecyclerView.
     */
    private void fetchCapturedPokemons() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid(); // Obtener el ID del usuario actual

        // Obtener la colección de Pokémon capturados
        db.collection("pokemons")
                .document(uid)
                .collection("userPokemons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear(); // Limpiar la lista local
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        PokemonCaptured pokemon = doc.toObject(PokemonCaptured.class); // Convertir el documento a un objeto
                        if (pokemon != null) {
                            capturedPokemonList.add(pokemon); // Agregar a la lista local
                        }
                    }
                    // Ordenar la lista por el atributo 'order'
                    capturedPokemonList.sort((p1, p2) -> Integer.compare(p1.getOrder(), p2.getOrder()));
                    adapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                })
                .addOnFailureListener(e -> Log.e("CapturedFragment", "Error al cargar Pokémon capturados", e)); // Manejar errores
    }
}
