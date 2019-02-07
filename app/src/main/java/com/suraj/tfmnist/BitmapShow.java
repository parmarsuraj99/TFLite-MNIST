package com.suraj.tfmnist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import com.suraj.tfmnist.Classifier;

import java.io.IOException;

public class BitmapShow extends AppCompatActivity {


    Bitmap bitmap;
    Classifier mnistClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_bitmap_show);
        init();



        if (getIntent().hasExtra("byteArray")) {
          //  ImageView previewThumbnail = findViewById(R.id.imageView2);
            bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent()
                            .getByteArrayExtra("byteArray").length);
        //    previewThumbnail.setImageBitmap(bitmap);
            Bitmap inverted = ImageUtil.invert(bitmap);
        //    previewThumbnail.setImageBitmap(inverted);

            if(mnistClassifier==null){
                Log.d("Innference", "onCreate: No mnist classifier created");
                return;
            }
            Result result = mnistClassifier.classify(inverted);
            //Toast.makeText(getApplicationContext(), "Inference Success", Toast.LENGTH_SHORT).show();
            renderResult(result);
        }

    }
    private void renderResult(Result result) {
        Toast.makeText(getApplicationContext(), result.getNumber()+"", Toast.LENGTH_SHORT).show();

    }

    private void init() {
        try {
            mnistClassifier = new Classifier(this);
        } catch (IOException e) {
            Toast.makeText(this,"Failed to create Classifier", Toast.LENGTH_LONG).show();
            Log.e("Init", "init(): Failed to create tflite model", e);
        }
    }
}
