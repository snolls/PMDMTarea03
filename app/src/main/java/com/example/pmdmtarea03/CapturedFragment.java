package com.example.pmdmtarea03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.example.pmdmtarea03.databinding.FragmentToolsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CapturedFragment extends Fragment implements CapturadosAdapter.OnPokemonDeleteListener{

    private FragmentCapturedBinding binding;
    private CapturadosAdapter adapter;
    private List<Pokemon> capturedPokemonList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar View Binding
        binding = FragmentCapturedBinding.inflate(inflater, container, false);

        // Configurar RecyclerView
        binding.recyclerViewCaptured.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CapturadosAdapter(capturedPokemonList, (CapturadosAdapter.OnPokemonDeleteListener) this);
        binding.recyclerViewCaptured.setAdapter(adapter);

        //recupera los pokemons capturados
        fetchCapturedPokemons();

        // Retornar la vista raíz del binding
        return binding.getRoot();
    }

    private void fetchCapturedPokemons() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        // Referencia a la colección de Pokémon capturados
        db.collection("pokemons")
                .document(uid)
                .collection("userPokemons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        // Convertir el documento en un objeto Pokemon
                        Pokemon pokemon = document.toObject(Pokemon.class);

                        // Validar que el objeto no sea nulo antes de agregarlo a la lista
                        if (pokemon != null) {
                            capturedPokemonList.add(pokemon);
                        } else {
                            Log.w("PokemonData", "Documento nulo o mal formateado: " + document.getId());
                        }
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error al recuperar Pokémon", e));
    }
    @Override
    public void onPokemonDeleted(Pokemon pokemon) {
        // Lógica para manejar la eliminación del Pokémon
        Toast.makeText(getContext(), "Pokémon eliminado: " + pokemon.getName(), Toast.LENGTH_SHORT).show();
    }
}