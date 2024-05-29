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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodplanner.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.vision.Detector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OcrActivity extends AppCompatActivity {
    SurfaceView cameraView;
    TextView textView;
    Button button;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    String detectedText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);



        cameraView = (SurfaceView) findViewById(R.id.surfaceView);
        textView = (TextView) findViewById(R.id.text_view);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (detectedText != null && !detectedText.isEmpty()) {
                    String userName = "Pepe";
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Users").child(userName);

                    myRef.child("Stock").setValue(detectedText);
                    Toast.makeText(getApplicationContext(), "Datos insertados en la base de datos.", Toast.LENGTH_LONG).show();
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