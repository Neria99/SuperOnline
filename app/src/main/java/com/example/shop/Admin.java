package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    private  ListView lvAdmin ;
    public static ArrayList<Item> arrShopAdmin = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button btAddM = findViewById(R.id.btAddM);
        Button btAddByCamera = findViewById(R.id.btAddC);
        Button btBack = findViewById(R.id.btBack);
        lvAdmin = findViewById(R.id.lvAdmin);
        getAllItem();

        lvAdmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = arrShopAdmin.get(position);
//                for(Item item : arrShopAdmin){
//                    if(item.name.equals(selectedItem.name))
                //item = null;
//                        myRef.child("key").child("items").removeValue(item);
//               }
                arrShopAdmin.remove(selectedItem);
                Shop.arrShop.remove(selectedItem);
                MyArrAdpter adp = new MyArrAdpter(Admin.this, android.R.layout.simple_list_item_1,arrShopAdmin);
                lvAdmin.setAdapter(adp);
                Toast.makeText(Admin.this, selectedItem.name + " המוצר ירד מסל הקניות", Toast.LENGTH_SHORT).show();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btAddM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this,AddItem.class);
                startActivity(intent);
            }
        });
        btAddByCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Admin.this,AddingItembyCamera.class);
                startActivity(intent);
            }
        });
    }
    private void getAllItem(){
        Query q = myRef.child("key").child("items");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // ArrayList<Item> arr = new ArrayList<>();
                arrShopAdmin.clear();
                for (DataSnapshot dsitem : snapshot.getChildren()) {
                    arrShopAdmin.add(dsitem.getValue(Item.class));
                }
                refresh_lv(arrShopAdmin);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private  void refresh_lv(ArrayList<Item> arr) {
        MyArrAdpter adp = new MyArrAdpter(Admin.this, android.R.layout.simple_list_item_1, arr);
        lvAdmin.setAdapter(adp);
    }
}