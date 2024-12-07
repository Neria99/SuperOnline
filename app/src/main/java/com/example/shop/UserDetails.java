package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserDetails extends AppCompatActivity {
    private  EditText edName;
    private  EditText edAddress;
    private  EditText edEmail;
    private  EditText edTel;

   private TextView tvS ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        tvS = findViewById(R.id.tvSum);
        Button btSend = findViewById(R.id.btSend);
        EditText edPay = findViewById(R.id.edPay);
        edName = findViewById(R.id.edName);
        edAddress = findViewById(R.id.edAddress);
        edEmail = findViewById(R.id.edEmail);
        edTel = findViewById(R.id.edTel);

        tvS.setText("The total " + Wagon.sum );

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dbOrder();
                Intent intent1 = new Intent(UserDetails.this,Invitation.class);
                startActivity(intent1);
            }
        });
    }
    private void dbOrder(){
        Order.name = edName.getText().toString();
        Order.address = edAddress.getText().toString();
        Order.email = edEmail.getText().toString();
        Order.tel = edTel.getText().toString();
    }
}