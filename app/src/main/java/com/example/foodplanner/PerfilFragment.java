package com.example.foodplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

public class PerfilFragment extends Fragment {

    private EditText intextNombre, intextApellido, intextUsuario, intextEmail, intextSub, intextAlergia;
    private Button btnGuardar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicializar vistas
        intextNombre = rootView.findViewById(R.id.intextnombre);
        intextApellido = rootView.findViewById(R.id.intextapellido);
        intextUsuario = rootView.findViewById(R.id.intextusuario);
        intextEmail = rootView.findViewById(R.id.intextemail);
        intextSub = rootView.findViewById(R.id.intextsub);
        intextAlergia = rootView.findViewById(R.id.intextalergia);
        btnGuardar = rootView.findViewById(R.id.guardar);

        // Configurar clic del botón "Guardar Cambios"
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes implementar la lógica para guardar los cambios del perfil
            }
        });

        return rootView;
    }
}
