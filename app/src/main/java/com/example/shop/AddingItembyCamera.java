package com.example.shop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class AddingItembyCamera extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    DatabaseReference myRef = database.getReference("");
    StorageReference sRef = storage.getReference();
    Button btnCamera;
    Button btBack;
    ImageView imgCamera;
    EditText edNewNameC;
    EditText edNewPriceC;
    EditText edNewTypeC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_itemby_camera);
        btnCamera = findViewById(R.id.btCamera);
        btBack= findViewById(R.id.btBackA);
        imgCamera = findViewById(R.id.imgCamera);
        edNewNameC = findViewById(R.id.edNewNameC);
        edNewPriceC = findViewById(R.id.edNewPriceC);
        edNewTypeC = findViewById(R.id.edNewTypeC);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePictureIntent, 123);
            }
        });
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddingItembyCamera.this,Admin.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 123 && resultCode == RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgCamera.setImageBitmap(photo);

            Item item = new Item();
            item.name = edNewNameC.getText().toString();;
            item.price = Double.parseDouble(edNewPriceC.getText().toString());
            item.type = edNewTypeC.getText().toString();
            uploadPicToStorage(photo, item);


        }
    }
    String url = "";

    private void uploadPicToStorage(Bitmap bitmap, Item item) {

        //breaking the bitmap to byte[]
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        //creating a path to save inside the storage
        StorageReference imageRef = sRef.child("images/"+randomName()+".jpg");


        //upload the byte[]
        UploadTask uploadTask = imageRef.putBytes(data);


        //taking the url and saving the url in the database
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL

                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {

                    Uri downloadUri = task.getResult();

                    url = downloadUri.toString();

                   item.link = url;


                    myRef.child("key").child("items").child(item.getName()).setValue(item);
                    Toast.makeText(AddingItembyCamera.this, item.name + "יתווסף בהצלחה לסל הקניות", Toast.LENGTH_SHORT).show();
                //    myRef.child("Items").push().setValue(item);


                } else {
                    // Handle failures
                    // ...
                }
            }
        });
    }
    private String randomName() {
        return UUID.randomUUID().toString();

    }

}