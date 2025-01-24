package com.example.pmdmtarea03;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.pmdmtarea03.databinding.ItemPokemonBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    public interface OnPokemonCapturedListener {
        void onPokemonCaptured();
    }
    private final List<Pokemon> pokemonList;
    private final List<Pokemon> capturedPokemonList; // Lista de Pokémon capturados
    private final OnPokemonCapturedListener listener; // Callback al fragmento

    public PokemonAdapter(List<Pokemon> pokemonList, List<Pokemon> capturedPokemonList, OnPokemonCapturedListener listener) {
        this.pokemonList = pokemonList;
        this.capturedPokemonList = capturedPokemonList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usa View Binding para inflar el layout
        ItemPokemonBinding binding = ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.binding.tvname.setText(pokemon.getName());
        holder.binding.tvnumero.setText(String.valueOf(pokemon.getOrder()));

        // Cargar la imagen con Glide o Picasso
        Glide.with(holder.binding.getRoot().getContext())
                .load(pokemon.getSprites().getOther().getHome().getFrontDefault())
                .into(holder.binding.pokemonImage);

        // Verificar si el Pokémon está capturado
        boolean isCaptured = capturedPokemonList.stream()
                .anyMatch(captured -> captured.getOrder() == pokemon.getOrder());

        if (isCaptured) {
            // Cambiar apariencia a rojo y deshabilitar clics
            holder.binding.cardView.setCardBackgroundColor(Color.RED);
            holder.binding.cardView.setEnabled(false);
        } else {
            // Restaurar apariencia normal para Pokémon no capturados
            holder.binding.cardView.setCardBackgroundColor(Color.WHITE);
            holder.binding.cardView.setEnabled(true);

            holder.binding.cardView.setOnClickListener(view -> {
                // Aquí puedes manejar el evento de clic para capturar al Pokémon
                savePokemonToFirebase(pokemon, holder.binding.getRoot());
            });
        }

    }

    // Método para guardar el Pokémon en Firebase
    private void savePokemonToFirebase(Pokemon pokemon, LinearLayout view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();

        Map<String, Object> pokemonData = new HashMap<>();
        pokemonData.put("name", pokemon.getName());
        pokemonData.put("order", pokemon.getOrder());
        pokemonData.put("height", pokemon.getHeight());
        pokemonData.put("weight", pokemon.getWeight());
        pokemonData.put("type", pokemon.getTypes().get(0).getType().getName());
        pokemonData.put("image", pokemon.getSprites().getOther().getHome().getFrontDefault());

        // Guardar los datos en una subcolección por usuario
        db.collection("pokemons")
                .document(uid) // Crear un documento por usuario
                .collection("userPokemons") // Subcolección para los Pokémon del usuario
                .add(pokemonData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(view.getContext(), "Pokémon guardado para este usuario", Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onPokemonCaptured();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(view.getContext(), "Error al guardar en Firebase", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // View Binding para acceder a las vistas
        final ItemPokemonBinding binding;

        public ViewHolder(@NonNull ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
