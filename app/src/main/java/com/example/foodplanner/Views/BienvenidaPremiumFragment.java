package com.example.foodplanner.Views;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.example.foodplanner.R;

public class BienvenidaPremiumFragment extends Fragment {

    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bienvenidapremium, container, false);

        // Obtener el nombre de usuario del argumento del fragmento
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }
        return view;
    }


}
