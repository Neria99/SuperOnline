package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;

public class AddItem extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    private static ArrayList<Item> arrShop = Shop.arrShop;
    private static ArrayList<Item> arrShopAdmin = Admin.arrShopAdmin;
    private  EditText tvNewName;
    private EditText tvNewPrice;
    private EditText tvNewType;
    private EditText tvNewLink;
    private Button btEnter;
    private Button btBackAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        tvNewName = findViewById(R.id.edNewName);
        tvNewPrice = findViewById(R.id.edNewPrice);
        tvNewType = findViewById(R.id.edNewType);
        tvNewLink = findViewById(R.id.edNewLink);
        btEnter = findViewById(R.id.btEnter);
        btBackAdmin = findViewById(R.id.btBackAdmin);


        btEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item newItem = new Item();
                newItem.price = Double.parseDouble(tvNewPrice.getText().toString());
                newItem.type= tvNewType.getText().toString();
                newItem.name = tvNewName.getText().toString();
                newItem.link = tvNewLink.getText().toString();

                myRef.child("key").child("items").push().setValue(newItem);
                Toast.makeText(AddItem.this, newItem.name + "יתווסף בהצלחה לסל הקניות", Toast.LENGTH_SHORT).show();
                tvNewPrice.setText("Enter price:");
                tvNewName.setText("Enter name:");
                tvNewLink.setText("Enter link:");
                tvNewType.setText("Enter type:");
            }
        });
        btBackAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddItem.this,Admin.class);
                startActivity(intent);
            }
        });
    }
}