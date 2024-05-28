package com.example.foodplanner.Views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodplanner.Models.EmailSender;
import com.example.foodplanner.R;

public class OcrFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ocr, container, false);

        // Obtener referencia al botón
        Button sendEmailButton = view.findViewById(R.id.sendEmailButton);

        // Configurar el evento de clic del botón
        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para enviar el correo
                enviarCorreo();
            }
        });

        return view;    }

    private void enviarCorreo() {
        // Información del correo
        String destinatario = "danidd343434@gmail.com";
        String asunto = "Asunto del correo";
        String cuerpo = "Este es el cuerpo del correo electrónico.";

        // Llamar al método estático de la clase EmailSender
        EmailSender.enviarCorreo(destinatario, asunto, cuerpo);
    }

}