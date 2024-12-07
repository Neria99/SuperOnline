package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference myRef = database.getReference("");
    StorageReference sRef = storage.getReference();

    //Server >>>>
    //Defines a service and connects to it
    private MyServiceMessages myServiceMessages;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder mybinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MyServiceMessages.SimpleBinder binder = (MyServiceMessages.SimpleBinder) mybinder;
            myServiceMessages= binder.getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
        }
    };
    public static int counter = 0;
    Random rand = new Random();
   public static  int randomInvit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Server Permissions >>>
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[] {Manifest.permission.POST_NOTIFICATIONS},
                111);



        ImageButton imageNext = findViewById(R.id.imageNext);
       // Button btNext = findViewById(R.id.btNext);
        Button btAdmin = findViewById(R.id.btAdmin);
        randomInvit = rand.nextInt(991) + 10;

        imageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Shop.class);
                startActivity(intent);

            }
        });
        btAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Admin.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Intent intent = new Intent(MainActivity.this, MyServiceMessages.class);
        startService(intent);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }
}