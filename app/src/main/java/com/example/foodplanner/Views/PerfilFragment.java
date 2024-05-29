package com.example.foodplanner.Views;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerfilFragment extends Fragment {

    private EditText intextNombre, intextApellido, intextUsuario, intextEmail, intextSub, intextAlergia;
    private Button btnGuardar;

    private DatabaseReference databaseReference;
    private String usuarioiniciado;

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

        // Configurar el campo de suscripción como no editable
        intextSub.setText("normal");
        intextSub.setFocusable(false);
        intextSub.setFocusableInTouchMode(false); // Esto asegura que no sea editable

        // Configurar el campo de alergias para desplazamiento
        intextAlergia.setVerticalScrollBarEnabled(true);
        intextAlergia.setMovementMethod(new ScrollingMovementMethod()); // Asegura que el desplazamiento funcione

        // Obtener el nombre de usuario pasado desde el fragmento anterior
        if (getArguments() != null) {
            usuarioiniciado = getArguments().getString("usuario");
        }

        if (usuarioiniciado != null && !usuarioiniciado.isEmpty()) {
            // Inicializar referencia a la base de datos de Firebase
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(usuarioiniciado);

            // Cargar la información del usuario desde Firebase Database
            loadUserInfo();
        } else {
            Toast.makeText(getActivity(), "Error: Usuario no encontrado.", Toast.LENGTH_SHORT).show();
        }

        // Configurar clic del botón "Guardar Cambios"
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Guardar los cambios del perfil en Firebase Database
                saveUserInfo();
            }
        });

        return rootView;
    }

    private void loadUserInfo() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String nombre = snapshot.child("nombre").getValue(String.class);
                    String apellido = snapshot.child("apellido").getValue(String.class);
                    String usuario = snapshot.child("usuario").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    ArrayList<String> alergiasList = (ArrayList<String>) snapshot.child("alergias").getValue();

                    intextNombre.setText(nombre);
                    intextApellido.setText(apellido);
                    intextUsuario.setText(usuario);
                    intextEmail.setText(email);
                    if (alergiasList != null) {
                        intextAlergia.setText(TextUtils.join(", ", alergiasList));
                    }
                } else {
                    Toast.makeText(getActivity(), "No se encontraron datos del usuario.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error al cargar los datos del usuario: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserInfo() {
        String nombre = intextNombre.getText().toString().trim();
        String apellido = intextApellido.getText().toString().trim();
        String usuario = intextUsuario.getText().toString().trim();
        String email = intextEmail.getText().toString().trim();
        String alergia = intextAlergia.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(apellido) || TextUtils.isEmpty(usuario) || TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir la cadena de alergias a una lista
        List<String> alergiasList = new ArrayList<>(Arrays.asList(alergia.split(",\\s*")));

        // Actualizar los datos del usuario en Firebase Database
        databaseReference.child("nombre").setValue(nombre);
        databaseReference.child("apellido").setValue(apellido);
        databaseReference.child("usuario").setValue(usuario);
        databaseReference.child("email").setValue(email);
        databaseReference.child("alergias").setValue(alergiasList).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getActivity(), "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Error al actualizar los datos.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
