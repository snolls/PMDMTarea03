package com.example.pmdmtarea03;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CapturedFragment extends Fragment {

    private FragmentCapturedBinding binding;
    private CapturadosAdapter adapter;
    private List<PokemonCaptured> capturedPokemonList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCapturedBinding.inflate(inflater, container, false);

        // Configurar RecyclerView
        binding.recyclerViewCaptured.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CapturadosAdapter(capturedPokemonList, pokemon -> {
            // Navegar a DetailsFragment con los datos del Pokémon seleccionado
            Bundle bundle = new Bundle();
            bundle.putString("name", pokemon.getName());
            bundle.putInt("order", pokemon.getOrder());
            bundle.putInt("height", pokemon.getHeight());
            bundle.putInt("weight", pokemon.getWeight());
            bundle.putString("type", pokemon.getType());
            bundle.putString("image", pokemon.getImage());

            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main, detailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
        binding.recyclerViewCaptured.setAdapter(adapter);

        // Cargar los Pokémon capturados
        fetchCapturedPokemons();

        return binding.getRoot();
    }


    private void fetchCapturedPokemons() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        db.collection("pokemons")
                .document(uid)
                .collection("userPokemons")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    capturedPokemonList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        PokemonCaptured pokemon = doc.toObject(PokemonCaptured.class);
                        System.out.println(pokemon);
                        if (pokemon != null) {
                            capturedPokemonList.add(pokemon);
                        }
                    }
                    // Ordenar la lista por el atributo 'order'
                    capturedPokemonList.sort((p1, p2) -> Integer.compare(p1.getOrder(), p2.getOrder()));
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("CapturedFragment", "Error al cargar Pokémon capturados", e));
    }
}

