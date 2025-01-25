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

/**
 * Adaptador para mostrar la lista de Pokémon en un RecyclerView.
 * Permite gestionar los eventos de captura de Pokémon y actualiza su estado visual.
 */
public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.ViewHolder> {

    /**
     * Interfaz para manejar eventos cuando un Pokémon es capturado.
     */
    public interface OnPokemonCapturedListener {
        void onPokemonCaptured(); // Método a implementar por el fragmento que utiliza el adaptador
    }

    private final List<Pokemon> pokemonList; // Lista de todos los Pokémon a mostrar
    private final List<Pokemon> capturedPokemonList; // Lista de Pokémon ya capturados por el usuario
    private final OnPokemonCapturedListener listener; // Callback para notificar capturas al fragmento

    /**
     * Constructor del adaptador.
     *
     * @param pokemonList          Lista de Pokémon a mostrar.
     * @param capturedPokemonList  Lista de Pokémon capturados.
     * @param listener             Listener para manejar capturas.
     */
    public PokemonAdapter(List<Pokemon> pokemonList, List<Pokemon> capturedPokemonList, OnPokemonCapturedListener listener) {
        this.pokemonList = pokemonList;
        this.capturedPokemonList = capturedPokemonList;
        this.listener = listener;
    }

    /**
     * Infla el layout de cada elemento del RecyclerView.
     *
     * @param parent   El contenedor padre.
     * @param viewType Tipo de vista (no utilizado aquí porque es un único tipo).
     * @return Un nuevo ViewHolder con el diseño inflado.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Usa View Binding para inflar el layout del elemento
        ItemPokemonBinding binding = ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    /**
     * Vincula los datos del Pokémon al ViewHolder en la posición dada.
     *
     * @param holder   ViewHolder que contiene las vistas.
     * @param position Posición del Pokémon en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position); // Obtener el Pokémon actual
        holder.binding.tvname.setText(pokemon.getName().toUpperCase()); // Mostrar el nombre en mayúsculas
        holder.binding.tvnumero.setText(String.format("#%s", String.valueOf(pokemon.getOrder()))); // Mostrar el número

        // Cargar la imagen del Pokémon usando Glide
        Glide.with(holder.binding.getRoot().getContext())
                .load(pokemon.getSprites().getOther().getHome().getFrontDefault()) // URL de la imagen
                .into(holder.binding.pokemonImage); // ImageView donde se mostrará la imagen

        // Verificar si el Pokémon ya está capturado
        boolean isCaptured = capturedPokemonList.stream()
                .anyMatch(captured -> captured.getOrder() == pokemon.getOrder());

        if (isCaptured) {
            // Si el Pokémon está capturado, cambiar el color del fondo a rojo y deshabilitar clics
            holder.binding.cardView.setCardBackgroundColor(Color.RED);
            holder.binding.cardView.setEnabled(false);
        } else {
            // Si no está capturado, restaurar el fondo blanco y habilitar clics
            holder.binding.cardView.setCardBackgroundColor(Color.WHITE);
            holder.binding.cardView.setEnabled(true);

            // Manejar el clic en el Pokémon para capturarlo
            holder.binding.cardView.setOnClickListener(view -> {
                savePokemonToFirebase(pokemon, holder.binding.getRoot()); // Guardar en Firebase
            });
        }
    }

    /**
     * Guarda el Pokémon capturado en Firebase Firestore.
     *
     * @param pokemon Pokémon a capturar.
     * @param view    Vista raíz para obtener el contexto.
     */
    private void savePokemonToFirebase(Pokemon pokemon, LinearLayout view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid(); // Obtener el ID del usuario autenticado

        // Crear un mapa con los datos del Pokémon
        Map<String, Object> pokemonData = new HashMap<>();
        pokemonData.put("name", pokemon.getName());
        pokemonData.put("order", pokemon.getOrder());
        pokemonData.put("height", pokemon.getHeight());
        pokemonData.put("weight", pokemon.getWeight());
        pokemonData.put("type", pokemon.getTypes().get(0).getType().getName()); // Tipo principal
        pokemonData.put("image", pokemon.getSprites().getOther().getHome().getFrontDefault()); // URL de la imagen

        // Obtener mensajes traducidos desde strings.xml
        String successMessage = view.getContext().getString(R.string.pokemon_saved);
        String errorMessage = view.getContext().getString(R.string.error_saving_pokemon);

        // Guardar los datos en Firestore
        db.collection("pokemons")
                .document(uid) // Documento único para cada usuario
                .collection("userPokemons") // Subcolección para Pokémon capturados
                .add(pokemonData) // Agregar los datos del Pokémon
                .addOnSuccessListener(documentReference -> {
                    // Mostrar mensaje de éxito y notificar al fragmento
                    Toast.makeText(view.getContext(), successMessage, Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onPokemonCaptured(); // Notificar que un Pokémon fue capturado
                    }
                })
                .addOnFailureListener(e -> {
                    // Mostrar mensaje de error
                    Toast.makeText(view.getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return Tamaño de la lista de Pokémon.
     */
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    /**
     * Clase ViewHolder que contiene las vistas de cada elemento del RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemPokemonBinding binding; // Binding para las vistas del elemento

        public ViewHolder(@NonNull ItemPokemonBinding binding) {
            super(binding.getRoot());
            this.binding = binding; // Inicializar el binding
        }
    }
}
