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
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;


public class DetailsFragment extends Fragment {
    private FragmentDetailsBinding binding;


    public DetailsFragment() {
        // Required empty public constructor
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
        // Inicializar el mapa de tipos e imágenes
        initializeTypeImageMap();

        // Obtener los datos del Bundle
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("name");
            int order = args.getInt("order");
            int height = args.getInt("height");
            int weight = args.getInt("weight");
            String type = args.getString("type");
            String image = args.getString("image");

            // Configurar los datos en la UI
            binding.tvName.setText(name);
            binding.tvNumber.setText("#"+String.valueOf(order));
            binding.pesoTv.setText(String.valueOf(height));
            binding.alturaTv.setText(String.valueOf(weight));
            // Cargar la imagen del tipo
            Integer typeImageResId = typeImageMap.get(type);
            if (typeImageResId != null) {
                binding.tipoPokemon.setImageResource(typeImageResId);
            }
            // Cargar la imagen usando Glide
            Glide.with(requireContext())
                    .load(image)
                    .into((ImageView) binding.rectangularImage); // Asegúrate de que este ID coincida con el XML
        }

        // Configurar el botón de retroceso
        binding.btnBack.setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack(); // Regresa al fragmento anterior
        });

        return binding.getRoot();
    }

    private Map<String, Integer> typeImageMap;

    private void initializeTypeImageMap() {
        typeImageMap = new HashMap<>();
        typeImageMap.put("bug", R.drawable.bug_type);
        typeImageMap.put("dragon", R.drawable.dragon_type);
        typeImageMap.put("electric", R.drawable.electric_type);
        typeImageMap.put("fairy", R.drawable.psychic_type);
        typeImageMap.put("fighting", R.drawable.fighting_type);
        typeImageMap.put("fire", R.drawable.fire_type);
        typeImageMap.put("flying", R.drawable.flying_type);
        typeImageMap.put("ghost", R.drawable.ghost_type);
        typeImageMap.put("grass", R.drawable.grass_type);
        typeImageMap.put("ground", R.drawable.ground_type);
        typeImageMap.put("ice", R.drawable.ice_type);
        typeImageMap.put("poison", R.drawable.poison_type);
        typeImageMap.put("psychic", R.drawable.psychic_type);
        typeImageMap.put("rock", R.drawable.rock_type);
        typeImageMap.put("water", R.drawable.water_type);

        // Añade más tipos según sea necesario
    }

}