package com.suraj.tfmnist;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static com.suraj.tfmnist.SimpleDrawingView.loadBitmapFromView;
import static com.suraj.tfmnist.SimpleDrawingView.scaleDown;


public class MainActivity extends AppCompatActivity {

    SimpleDrawingView mDrawingView;
    Classifier mnistClassifier;

    String class_mapping = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabdefghnqrt";

    TextView pred;
    TextView probs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pred = findViewById(R.id.textView3);
        probs= findViewById(R.id.textView4);

        init();

        mDrawingView=findViewById(R.id.simpleDrawingView);

        Button clearBtn = findViewById(R.id.button2);
        final Button procBtn = findViewById(R.id.button3);


        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.clearCanvas();
                pred.setText("");
                probs.setText("");
            }
        });

        procBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = loadBitmapFromView(mDrawingView);
          //      Toast.makeText(getApplicationContext(),"Bitmap Created", Toast.LENGTH_LONG).show();

                b= scaleDown(b, 28, true);
                //Bitmap inverted = ImageUtil.invert(b);

                    // previewThumbnail.setImageBitmap(bitmap);
                    Bitmap inverted = ImageUtil.invert(b);

                    if(mnistClassifier==null){
                        Log.d("Innference", "onCreate: No mnist classifier created");
                        return;
                    }
                    Result result = mnistClassifier.classify(inverted);
                    //Toast.makeText(getApplicationContext(), "Inference Success", Toast.LENGTH_SHORT).show();
                //    renderResult(result, pred);
                    pred.setText(""+class_mapping.charAt(result.getNumber()));
                    probs.setText(""+result.getProbability());
                }

        });
    }

    private void init() {
        try {
            mnistClassifier = new Classifier(this);
        } catch (IOException e) {
            Toast.makeText(this,"Failed to create Classifier", Toast.LENGTH_LONG).show();
            Log.e("Init", "init(): Failed to create tflite model", e);
        }
    }
    private void renderResult(Result result, TextView tv) {
        Toast.makeText(getApplicationContext(), result.getNumber()+"", Toast.LENGTH_SHORT).show();
    }
}
