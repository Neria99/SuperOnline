package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Wagon extends AppCompatActivity {

    private ListView lvW;
    private TextView tvCountW;
    private  TextView tvSum;
    private static ArrayList<Item> wagonItems = Shop.arrWagon;

    public static double sum = countSum(wagonItems);
    private Button btBack ;
    private Button btPay ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wagon);
        btBack = findViewById(R.id.btBack);
        btPay = findViewById(R.id.btPay);
        lvW = findViewById(R.id.lvW);
        tvCountW = findViewById(R.id.tvCounterW);
        tvSum = findViewById(R.id.tvSum);
        refresh_lv(wagonItems);
        tvCountW.setText("the number of items in the cart  " + MainActivity.counter);
        tvSum.setText("The total " + sum);


        lvW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = wagonItems.get(position);
                wagonItems.remove(selectedItem);
                MyArrAdpter adp = new MyArrAdpter(Wagon.this, android.R.layout.simple_list_item_1, wagonItems);
                lvW.setAdapter(adp);
                MainActivity.counter -=1;
                tvCountW.setText("the number of items in the cart  " + MainActivity.counter);
                sum = sum - selectedItem.price;
                tvSum.setText("The total " + sum);
                Toast.makeText(Wagon.this, selectedItem.name + " ירד מהעגלה", Toast.LENGTH_SHORT).show();
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wagon.this, Shop.class);
                startActivity(intent);
            }
        });
        btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Wagon.this,UserDetails.class);
                startActivity(intent);
            }
        });
    }
    private static double countSum(ArrayList<Item> arr){
        double sum = 0;
        for (Item item : arr) {
            sum += item.price;
        }
        return sum;
    }
    private void refresh_lv(ArrayList<Item> arr) {
        MyArrAdpter adp = new MyArrAdpter(Wagon.this, android.R.layout.simple_list_item_1, arr);
        lvW.setAdapter(adp);
    }
}