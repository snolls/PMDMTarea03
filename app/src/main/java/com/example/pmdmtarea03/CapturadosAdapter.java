package com.example.pmdmtarea03;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.pmdmtarea03.databinding.ItemPokemonBinding;
import java.util.List;

/**
 * Adaptador para mostrar la lista de Pokémon capturados en un RecyclerView.
 */
public class CapturadosAdapter extends RecyclerView.Adapter<CapturadosAdapter.ViewHolder> {

    /**
     * Interfaz para manejar clics en los elementos del RecyclerView.
     */
    public interface OnItemClickListener {
        void onItemClick(PokemonCaptured pokemon); // Evento para manejar el clic en un Pokémon
    }

    private List<PokemonCaptured> pokemons; // Lista de Pokémon capturados a mostrar
    private OnItemClickListener listener; // Listener para manejar eventos de clic

    /**
     * Constructor del adaptador.
     *
     * @param pokemons Lista de Pokémon capturados.
     * @param listener Listener para manejar los clics en los elementos.
     */
    public CapturadosAdapter(List<PokemonCaptured> pokemons, OnItemClickListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
    }

    /**
     * Inflar el layout de cada elemento del RecyclerView.
     *
     * @param parent   El contenedor padre.
     * @param viewType Tipo de vista del RecyclerView.
     * @return Un nuevo ViewHolder que contiene el diseño inflado.
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el diseño del elemento usando View Binding
        ItemPokemonBinding binding = ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.getContext()), // Inflador desde el contexto del padre
                parent, // El contenedor donde se añadirá la vista
                false // No se añade directamente al contenedor
        );
        return new ViewHolder(binding);
    }

    /**
     * Vincula los datos de un Pokémon a un ViewHolder específico.
     *
     * @param holder   El ViewHolder donde se asignarán los datos.
     * @param position La posición del Pokémon en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Obtener el Pokémon actual basado en la posición
        PokemonCaptured pokemon = pokemons.get(position);

        // Asignar los datos al ViewHolder
        holder.binding.tvname.setText(pokemon.getName().toUpperCase()); // Nombre del Pokémon en mayúsculas
        holder.binding.tvnumero.setText(String.format("#%s", String.valueOf(pokemon.getOrder()))); // Número del Pokémon

        // Cargar la imagen del Pokémon usando Glide
        Glide.with(holder.binding.getRoot().getContext()) // Contexto desde la raíz del ViewHolder
                .load(pokemon.getImage()) // URL de la imagen
                .into(holder.binding.pokemonImage); // Imagen en el ImageView del layout

        // Configurar el clic en el elemento del RecyclerView
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(pokemon); // Notificar el clic al listener
            }
        });
    }

    /**
     * Devuelve el número total de elementos en la lista.
     *
     * @return Cantidad de Pokémon en la lista.
     */
    @Override
    public int getItemCount() {
        return pokemons.size();
    }

    /**
     * Clase ViewHolder que contiene las vistas de cada elemento del RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ItemPokemonBinding binding; // Binding para acceder a las vistas

        /**
         * Constructor del ViewHolder.
         *
         * @param binding Binding generado para el diseño del elemento.
         */
        public ViewHolder(@NonNull ItemPokemonBinding binding) {
            super(binding.getRoot()); // Llamar al constructor base con la vista raíz
            this.binding = binding; // Inicializar el binding
        }
    }
}
