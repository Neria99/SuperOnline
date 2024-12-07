package com.example.shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Shop extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");

    public static ArrayList<Item> arrShop = new ArrayList<>();
    private ArrayList<Item> filteredArrShop = new ArrayList<>();
    public static ArrayList<Item> arrWagon = new ArrayList<>();
    ListView lv ;
    private  Spinner spSortP;
    private  Spinner spSortT;
    private Button btW;
    private TextView tvCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        spSortP = findViewById(R.id.spSortP);
        spSortT = findViewById(R.id.spSortT);
        btW = findViewById(R.id.btWagon);
        tvCounter = findViewById(R.id.tvCounter);
        lv = findViewById(R.id.lv);
        tvCounter.setText("the number of items in the cart  " + MainActivity.counter);
        String[] optionsP = {"Sort by price","מהזול ליקר", "מהיקר לזול"};
        String[] optionsT = {"Sort by type" ,"מוצרי חלב","עופות", "שימורים", "גלידות", "ניקיון"};
        getAllItem(arrShop);
        getAllItem(filteredArrShop);

        //Inserting spinner options
        ArrayAdapter<String> adapterP = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionsP);
        adapterP.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortP.setAdapter(adapterP);
        //Inserting spinner options
        ArrayAdapter<String> adapterT = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionsT);
        adapterT.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSortT.setAdapter(adapterT);

        spSortP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = (String) parentView.getItemAtPosition(position);
                if("Sort by price".equals(selectedOption)){
                    refresh_lv(arrShop);
                } else if ("מהזול ליקר".equals(selectedOption)) {
                     Collections.sort(filteredArrShop, new PriceComparator(true));
                     refresh_lv(filteredArrShop);
                 }else if("מהיקר לזול".equals(selectedOption)){
                     Collections.sort(filteredArrShop, new PriceComparator(false));
                     refresh_lv(filteredArrShop);
                 }
            }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spSortT.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedType = (String) parentView.getItemAtPosition(position);
                filterByType(selectedType);
                refresh_lv(filteredArrShop);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = filteredArrShop.get(position);
                arrWagon.add(selectedItem);
                Toast.makeText(Shop.this, selectedItem.name + " התווסף לעגלה", Toast.LENGTH_SHORT).show();
                MainActivity.counter +=1;
                tvCounter.setText("the number of items in the cart  " + MainActivity.counter);
                myRef.child("key").child("Order").child("Order Number " + " " + MainActivity.randomInvit).setValue(arrWagon);
            }
        });

        btW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Shop.this, Wagon.class);
                startActivity(intent);
            }
        });
    }

    //Sort by type
    private ArrayList<Item> filterByType(String selectedType) {
        //Clears the array
        filteredArrShop.clear();
        if ("Sort by type".equals(selectedType)) {
            //Enter all the details for me
            filteredArrShop.addAll(arrShop);
        } else {
            for (Item item : arrShop) {
                if (item.type.equals(selectedType)) {
                    filteredArrShop.add(item);
                }
            }
        }
        return filteredArrShop;
    }

    //Insert items into the array
    public static void shopDB(ArrayList<Item> arr){
        Item item1 = new Item();
        item1.price = 11.4;
        item1.type = "מוצרי חלב";
        item1.name = "חלב";
        item1.link = "https://d3m9l0v76dty0.cloudfront.net/system/photos/10741829/original/05bc1781b848e90286be2f598f119dc7.jpg";
        arr.add(item1);

        Item item2 = new Item();
        item2.price = 5.9;
        item2.type = "מוצרי חלב";
        item2.name = "גבינה";
        item2.link ="https://www.shefab.co.il/files/products/product235_image1_2020-05-14_22-00-41.jpg";
        arr.add(item2);

        Item item3 = new Item();
        item3.price = 3.7;
        item3.type = "מוצרי חלב";
        item3.name = "מעדן";
        item3.link = "https://freezbee.co.il/wp-content/uploads/2022/07/%D7%A7%D7%99%D7%98%D7%95-%D7%A2%D7%95%D7%92%D7%AA-%D7%92%D7%91%D7%99%D7%A0%D7%94.jpg";
        arr.add(item3);

        Item item4 = new Item();
        item4.price = 24.5;
        item4.type = "עופות";
        item4.name = "עוף שלם";
        item4.link = "https://res.cloudinary.com/shufersal/image/upload/f_auto,q_auto/v1551800922/prod/product_images/products_large/MOZ48_L_P_9394788_1.png";
        arr.add(item4);

        Item item5 = new Item();
        item5.price = 29.0;
        item5.type = "עופות";
        item5.name = "כרעיים";
        item5.link = "https://res.cloudinary.com/shufersal/image/upload/f_auto,q_auto/v1551800922/prod/product_images/products_large/MPG46_L_P_9394795_1.png";
        arr.add(item5);

        Item item6 = new Item();
        item6.price = 11.2;
        item6.type = "שימורים";
        item6.name = "זיתיים";
        item6.link = "https://www.einstein-online.co.il/ProductsImages/thumbs/X157403_2852019161427_400_400.jpg";
        arr.add(item6);

        Item item7 = new Item();
        item7.price = 23.6;
        item7.type = "שימורים";
        item7.name = "טונה";
        item7.link = "https://res.cloudinary.com/shufersal/image/upload/f_auto,q_auto/v1551800922/prod/product_images/products_large/NHV48_L_P_7296073254102_1.png";
        arr.add(item7);

        Item item8 = new Item();
        item8.price = 26.4;
        item8.type = "גלידות";
        item8.name = "טילונים";
        item8.link = "https://macolet.net/Cat_387221_2065.jpg";
        arr.add(item8);

        Item item9 = new Item();
        item9.price = 15.8;
        item9.type ="ניקיון";
        item9.name = "חומר שטיפה";
        item9.link = "https://touchonline.co.il/wp-content/uploads/2021/06/Product_gallery_c_174.jpg";
        arr.add(item9);

        Item item10 = new Item();
        item10.price = 8.4;
        item10.type ="ניקיון";
        item10.name = "כפפות";
        item10.link = "https://dsfiga.co.il/Cat_398446_324.jpg";
        arr.add(item10);
    }
    private void getAllItem(ArrayList<Item> arr){
        Query q = myRef.child("key").child("items");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // ArrayList<Item> arr = new ArrayList<>();
                arr.clear();
                for (DataSnapshot dsitem : snapshot.getChildren()) {
                    arr.add(dsitem.getValue(Item.class));
                }
                refresh_lv(arr);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void refresh_lv(ArrayList<Item> arr) {
        MyArrAdpter adp = new MyArrAdpter(Shop.this, android.R.layout.simple_list_item_1, arr);
        lv.setAdapter(adp);
         }
}