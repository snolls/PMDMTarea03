package com.example.pmdmtarea03;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.pmdmtarea03.databinding.FragmentCapturedBinding;
import com.example.pmdmtarea03.databinding.FragmentDetailsBinding;
import com.example.pmdmtarea03.databinding.FragmentToolsBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Fragmento que muestra los detalles de un Pokémon capturado.
 */
public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding; // View Binding para acceder a las vistas del fragmento

    public DetailsFragment() {
        // Constructor público requerido para Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inicializar View Binding
        binding = FragmentDetailsBinding.inflate(inflater, container, false);

        // Inicializar el mapa que contiene las imágenes correspondientes a cada tipo de Pokémon
        initializeTypeImageMap();

        // Obtener los datos pasados a través del Bundle
        Bundle args = getArguments();
        if (args != null) {
            // Obtener los valores del Bundle
            String name = Objects.requireNonNull(args.getString("name")).toUpperCase(); // Nombre del Pokémon
            int order = args.getInt("order"); // Número del Pokémon
            int height = args.getInt("height"); // Altura del Pokémon
            int weight = args.getInt("weight"); // Peso del Pokémon
            String type = args.getString("type"); // Tipo del Pokémon
            String image = args.getString("image"); // URL de la imagen del Pokémon

            // Configurar los textos de las medidas
            String medida = getString(R.string.medida_altura); // Obtener unidad de altura desde los recursos
            binding.tvName.setText(name); // Asignar el nombre del Pokémon
            binding.tvNumber.setText(String.format("#%s", String.valueOf(order))); // Asignar el número del Pokémon
            binding.pesoTv.setText(String.format("%s %s", String.valueOf(height), medida)); // Configurar altura

            medida = getString(R.string.medida_peso); // Obtener unidad de peso desde los recursos
            binding.alturaTv.setText(String.format("%s %s", String.valueOf(weight), medida)); // Configurar peso

            // Cargar la imagen correspondiente al tipo del Pokémon
            Integer typeImageResId = typeImageMap.get(type);
            if (typeImageResId != null) {
                binding.tipoPokemon.setImageResource(typeImageResId); // Establecer la imagen según el tipo
            }

            // Cargar la imagen del Pokémon usando Glide
            Glide.with(requireContext())
                    .load(image) // URL de la imagen
                    .into((ImageView) binding.rectangularImage); // Asignar al ImageView correspondiente
        }

        // Configurar el botón de retroceso para regresar al fragmento anterior
        binding.btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(); // Navegar hacia atrás
        });

        return binding.getRoot(); // Retornar la vista raíz del fragmento
    }

    private Map<String, Integer> typeImageMap; // Mapa para relacionar tipos de Pokémon con imágenes

    /**
     * Inicializa el mapa que contiene los tipos de Pokémon y sus imágenes correspondientes.
     */
    private void initializeTypeImageMap() {
        typeImageMap = new HashMap<>();
        typeImageMap.put("bug", R.drawable.bug_type); // Tipo bicho
        typeImageMap.put("dragon", R.drawable.dragon_type); // Tipo dragón
        typeImageMap.put("electric", R.drawable.electric_type); // Tipo eléctrico
        typeImageMap.put("fairy", R.drawable.psychic_type); // Tipo hada
        typeImageMap.put("fighting", R.drawable.fighting_type); // Tipo lucha
        typeImageMap.put("fire", R.drawable.fire_type); // Tipo fuego
        typeImageMap.put("flying", R.drawable.flying_type); // Tipo volador
        typeImageMap.put("ghost", R.drawable.ghost_type); // Tipo fantasma
        typeImageMap.put("grass", R.drawable.grass_type); // Tipo planta
        typeImageMap.put("ground", R.drawable.ground_type); // Tipo tierra
        typeImageMap.put("ice", R.drawable.ice_type); // Tipo hielo
        typeImageMap.put("poison", R.drawable.poison_type); // Tipo veneno
        typeImageMap.put("psychic", R.drawable.psychic_type); // Tipo psíquico
        typeImageMap.put("rock", R.drawable.rock_type); // Tipo roca
        typeImageMap.put("water", R.drawable.water_type); // Tipo agua

        // Añadir más tipos según sea necesario
    }
}
