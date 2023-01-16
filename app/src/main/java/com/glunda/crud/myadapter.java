package com.glunda.crud;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder> {


       public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position, @NonNull model model) {

           holder.productname.setText(model.getProductName());
        holder.productsize.setText(model.getProductsize());
        holder.gender.setText(model.getGender());
        Glide.with(holder.img.getContext()).load(model.getUrl()).into(holder.img);
//update
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                        .setExpanded(true,1100)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText url = myview.findViewById(R.id.uimurl);
                EditText name = myview.findViewById(R.id.uiname);
                EditText productsize = myview.findViewById(R.id.usize);
                EditText gender = myview.findViewById(R.id.ugender);

                Button submit = myview.findViewById(R.id.submit);

                url.setText(model.getUrl());
                name.setText(model.getProductName());
                productsize.setText(model.getProductsize());
                gender.setText(model.getGender());

                dialogPlus.show();


                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("url",url.getText().toString());
                        map.put("productName",name.getText().toString());
                        map.put("productsize",productsize.getText().toString());
                        map.put("gender",gender.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Products")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.img.getContext());
                builder.setTitle("Delete");
                builder.setMessage("Delete??");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        FirebaseDatabase.getInstance().getReference().child("students").
                                child(getRef(position).getKey()).removeValue();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlelist,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        ImageView img,edit,delete;
        TextView productname, productsize,gender;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.img1);
            productname = itemView.findViewById(R.id.name);
            productsize = itemView.findViewById(R.id.produtsize);
            gender = itemView.findViewById(R.id.gender);


            edit = itemView.findViewById(R.id.editicon);
            delete = itemView.findViewById(R.id.deleteicon);
        }
    }
}
