package com.glunda.crud;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddData extends AppCompatActivity {

    EditText productName,productsize,gender,url;
    Button submit,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);


        productName = findViewById(R.id.addName);
        productsize = findViewById(R.id.addProductSize);
       gender = findViewById(R.id.addgender);
       url = findViewById(R.id.addurl);

        back = findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        submit = findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }
        });
    }

    private void processinsert() {

         Map<String,Object> map = new HashMap<>();
         map.put("produtName",productName.getText().toString());
         map.put("productsize",productsize.getText().toString());
         map.put("gender",gender.getText().toString());
         map.put("url",url.getText().toString());


         FirebaseDatabase.getInstance().getReference().child("Products").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        productName.setText("");
                        productsize.setText("");
                        gender.setText("");
                        url.setText("");
                        Toast.makeText(getApplicationContext(),"Inserted",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                   Toast.makeText(getApplicationContext(),"could not Insert",Toast.LENGTH_SHORT).show();
                    }
                });


    }
}