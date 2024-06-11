package com.example.foodplanner.Views;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.foodplanner.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PremiumFragment extends Fragment {

    private EditText titularEditText, cardNumberEditText, cardExpiryEditText, cardCvvEditText;
    private Button realizarPagoButton;
    private DatabaseReference mDatabase;
    private String username;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_premium, container, false);

        // Inicializar Database Reference
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        // Recoger los argumentos del fragmento
        if (getArguments() != null) {
            username = getArguments().getString("username");
        }

        // Referenciar los elementos de la vista
        titularEditText = view.findViewById(R.id.Titular);
        cardNumberEditText = view.findViewById(R.id.card_number);
        cardExpiryEditText = view.findViewById(R.id.card_expiry);
        cardCvvEditText = view.findViewById(R.id.card_cvv);
        realizarPagoButton = view.findViewById(R.id.realizar_pago);

        // Configurar el clic del botón de realizar pago
        realizarPagoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarPago();
            }
        });

        return view;
    }

    private void realizarPago() {
        String titular = titularEditText.getText().toString();
        String cardNumber = cardNumberEditText.getText().toString();
        String cardExpiry = cardExpiryEditText.getText().toString();
        String cardCvv = cardCvvEditText.getText().toString();

        if (titular.isEmpty() || cardNumber.isEmpty() || cardExpiry.isEmpty() || cardCvv.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar el tipo de suscripción en Firebase
        mDatabase.child(username).child("tiposuscripcion").setValue("premium")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Navegar a BienvenidaPremiumFragment
                        BienvenidaPremiumFragment bienvenidaPremiumFragment = new BienvenidaPremiumFragment();
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.infousu2, bienvenidaPremiumFragment)
                                .commit();
                    } else {
                        Toast.makeText(getContext(), "Error al actualizar suscripción", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
