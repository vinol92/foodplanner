package com.example.foodplanner.Models;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailSender {

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        executorService.execute(() -> {
            final String usuario = "foodplanner83@gmail.com"; // Cambia esto al correo electrónico del remitente
            final String contraseña = "FoodPlanner123"; // Cambia esto a la contraseña de tu correo electrónico

            // Configuración de las propiedades para la sesión de correo
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            // Creación de la sesión de correo
            Session session = Session.getInstance(props, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(usuario, contraseña);
                }
            });

            try {
                // Crear un mensaje de correo electrónico
                Message mensaje = new MimeMessage(session);
                mensaje.setFrom(new InternetAddress(usuario)); // Configurar el remitente
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario)); // Configurar el destinatario
                mensaje.setSubject(asunto); // Configurar el asunto
                mensaje.setText(cuerpo); // Configurar el cuerpo del mensaje

                // Enviar el mensaje
                Transport.send(mensaje);

                System.out.println("¡Correo electrónico enviado correctamente!");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
