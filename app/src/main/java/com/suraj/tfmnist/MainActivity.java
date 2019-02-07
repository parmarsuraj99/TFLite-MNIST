package com.suraj.tfmnist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.suraj.tfmnist.SimpleDrawingView.loadBitmapFromView;
import static com.suraj.tfmnist.SimpleDrawingView.scaleDown;


public class MainActivity extends AppCompatActivity {

    SimpleDrawingView mDrawingView;
    Classifier mnistClassifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawingView=findViewById(R.id.simpleDrawingView);

        Button clearBtn = findViewById(R.id.button2);
        Button procBtn = findViewById(R.id.button3);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawingView.clearCanvas();
            }
        });

        procBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap b = loadBitmapFromView(mDrawingView);
          //      Toast.makeText(getApplicationContext(),"Bitmap Created", Toast.LENGTH_LONG).show();

                b= scaleDown(b, 28, true);
                //Bitmap inverted = ImageUtil.invert(b);

                Intent i = new Intent(MainActivity.this, BitmapShow.class);
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 50, bs);
                i.putExtra("byteArray", bs.toByteArray());
                startActivity(i);
            }
        });



    }
}
