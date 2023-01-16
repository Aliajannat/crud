package com.glunda.crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    myadapter myadapter;
FloatingActionButton fb;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"),model.class)
                        .build();

        myadapter = new myadapter(options);
        recyclerView.setAdapter(myadapter);
        fb = findViewById(R.id.fadd);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),AddData.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myadapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String P) {
                processSearch(P);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String P) {
                processSearch(P);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);


    }

    private void processSearch(String P) {

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("gender").startAt(P).endAt(P+"\uf8ff"),model.class)
                        .build();

        myadapter = new myadapter(options);
        recyclerView.setAdapter(myadapter);
        myadapter.startListening();

    }
}