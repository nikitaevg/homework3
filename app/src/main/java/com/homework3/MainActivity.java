package com.homework3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final String fileName = "pic.jpg", IS = "MY_BROADCAST";
    ImageView image;
    BroadcastReceiver a, b;
    TextView err;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.picture);
        err = (TextView) findViewById(R.id.error_txt);
        File f = new File(getFilesDir(), fileName);
        if (f.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
            image.setImageBitmap(bitmap);
            image.setVisibility(View.VISIBLE);
            err.setVisibility(View.INVISIBLE);
        } else {
            image.setVisibility(View.INVISIBLE);
            err.setVisibility(View.VISIBLE);
        }
        a = new ScreenOnReceiver();
        b = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showImage();
            }
        };
        registerReceiver(a, new IntentFilter(Intent.ACTION_SCREEN_ON));
        registerReceiver(b, new IntentFilter(IS));
    }

    private void showImage() {
        File f = new File(getFilesDir(), fileName);
        Bitmap bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());
        if (f.exists()) {
            image.setImageBitmap(bitmap);
            image.setVisibility(View.VISIBLE);
            err.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(a);
        unregisterReceiver(b);
    }

}
