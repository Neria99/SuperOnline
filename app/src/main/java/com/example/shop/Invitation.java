package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

public class Invitation extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    Random rand = new Random();
    int randomNumber = rand.nextInt(9000) + 1000;
    private ArrayList<Order> invit = new ArrayList<>();
    TextView tvName ;
    TextView tvAddress ;
    TextView tvEmail ;
    TextView tvTel;
    TextView tvPay;
    Button btB;
    Button btWaze;
    Button btWhatsApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        btB= findViewById(R.id.btB);
        tvPay = findViewById(R.id.tvS);
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvEmail = findViewById(R.id.tvEmail);
        tvTel = findViewById(R.id.tvTel);
        btWaze = findViewById(R.id.btWaze);
        btWhatsApp = findViewById(R.id.btWhatsApp);


        tvPay.setText("Charged in total " + Wagon.sum);
        tvName.setText("Admission details Name = " + Order.name);
        tvAddress.setText("Address = " + Order.address);
        tvEmail.setText("Email = " + Order.email);
        tvTel.setText("Tel = " + Order.tel);

        Order newOrder = new Order();
        newOrder.pay = tvPay.getText().toString();
        newOrder.name = tvName.getText().toString();
        newOrder.address= tvAddress.getText().toString();
        newOrder.email = tvEmail.getText().toString();
        newOrder.tel = tvTel.getText().toString();

        myRef.child("key").child("Invitation").child("Invitation Number " + " " + MainActivity.randomInvit).setValue(newOrder);

        btB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Invitation.this,MainActivity.class);
                startActivity(intent);
            }
        });

        btWaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = "שדרות כצלנסון 47, קרית אתא";
                String uri = "waze://?q=" + Uri.encode(address);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
            }
        });
        btWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Hello order number = " + randomNumber  + " Name = " + Order.name + " Address = " + Order.address + " Email = " + Order.email + " Tel = " + Order.tel;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");

                sendIntent.putExtra("jid", "972506862163@s.whatsapp.net");
                try {
                    startActivity(sendIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Invitation.this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}