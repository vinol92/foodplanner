package com.example.foodplanner.Views;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodplanner.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.vision.Detector;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OcrActivity extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    CameraSource cameraSource;
    Button button;
    private DatabaseReference mDatabase;

    final int RequestCameraPermissionID = 1001;
    private String detectedText;
    private List<String> productNames = new ArrayList<>(Arrays.asList(
            "leche", "pan", "arroz", "huevos", "azúcar", "sal", "pimienta", "aceite de oliva",
            "mantequilla", "harina", "pollo", "carne de res", "pescado", "tomates", "cebollas",
            "ajo", "zanahorias", "patatas", "limones", "naranjas", "manzanas", "plátanos",
            "uvas", "espinacas", "lechuga", "brócoli", "guisantes", "judías verdes", "maíz",
            "champiñones", "pimientos", "calabacín", "berenjenas", "queso", "yogur", "miel",
            "vinagre", "mostaza", "ketchup", "salsa de soja", "curry", "canela", "vainilla",
            "chocolate", "nueces", "almendras", "pasta", "cuscús", "lentejas", "garbanzos",
            "tocino", "jamón"
    ));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        String userName = getIntent().getStringExtra("username");
        mDatabase = FirebaseDatabase.getInstance().getReference();


        cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        textView = (TextView) findViewById(R.id.text_view);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detectedText != null && !detectedText.isEmpty()) {
                    // Divide el texto detectado en líneas
                    String[] lines = detectedText.split("\\n");
                    for (String line : lines) {
                        // Divide cada línea en palabras
                        String[] parts = line.split("\\s+");
                        if (parts.length == 2) {
                            final String product = parts[0].substring(0, 1).toUpperCase() + parts[0].substring(1).toLowerCase();
                            final int amount;
                            try {
                                //Coge el amount en varios
                                amount = Integer.parseInt(parts[1]);
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Cantidad inválida para " + parts[0], Toast.LENGTH_LONG).show();
                                continue;
                            }
                            mDatabase.child("Users").child(userName).child("Stock").child(product).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    int newAmount = amount;
                                    if (snapshot.exists()) {
                                        Integer currentAmount = snapshot.child("amount").getValue(Integer.class);
                                        if (currentAmount != null) {
                                            newAmount += currentAmount;
                                        }
                                    }
                                    Map<String, Object> updates = new HashMap<>();
                                    updates.put("amount", newAmount);
                                    mDatabase.child("Users").child(userName).child("Stock").child(product).updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getApplicationContext(), product + " actualizado.", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "Error al actualizar " + product, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "Error en la base de datos: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(getApplicationContext(), "Formato incorrecto en la línea: " + line, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });



        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(getApplicationContext(), "Los servicios de Google Play no estÃ¡n disponibles.", Toast.LENGTH_LONG).show();
        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(640, 480)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();

            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(OcrActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {
                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {
                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {
                                List<TextBlock> textBlocks = new ArrayList<>();
                                for (int i = 0; i < items.size(); ++i) {
                                    textBlocks.add(items.valueAt(i));
                                }

                                // Ordena los bloques de texto por su posición
                                Collections.sort(textBlocks, new Comparator<TextBlock>() {
                                    @Override
                                    public int compare(TextBlock a, TextBlock b) {
                                        int diffOfTops = a.getBoundingBox().top - b.getBoundingBox().top;
                                        int diffOfLefts = a.getBoundingBox().left - b.getBoundingBox().left;
                                        if (diffOfTops != 0) {
                                            return diffOfTops;
                                        }
                                        return diffOfLefts;
                                    }
                                });

                                StringBuilder stringBuilder = new StringBuilder();
                                for (TextBlock textBlock : textBlocks) {
                                    stringBuilder.append(textBlock.getValue());
                                    stringBuilder.append("\n");
                                    detectedText = textBlock.getValue();
                                }
                                textView.setText(stringBuilder.toString());
                            }
                        });
                    }
                }
            });

        }
    }

    public void StartCamera() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(OcrActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    RequestCameraPermissionID);
            return;
        }
        try {
            cameraSource.start(cameraView.getHolder());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}