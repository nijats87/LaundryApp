package com.start.laundryapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.start.laundryapp.models.EditClothesModel;

import java.util.ArrayList;
import java.util.List;



public class ClothesRecyclerAdapter extends RecyclerView.Adapter<ClothesRecyclerAdapter.ViewHolder> {

    public List<EditClothesModel> data = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_rview, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        EditClothesModel model = data.get(position);
        holder.image.setImageURI(Uri.parse(model.imageUri));
        holder.clothesType.setText(model.clothName);
        holder.note.setText(model.note);


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setCancelable(false);
                alert.setTitle("Paltar siyahıdan silinsin?");
                alert.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteItem(position);
                    }
                })
                        .setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                final AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

         TextView clothesType, note;
         ImageView image, deleteBtn;

         ViewHolder(View view){
            super(view);
            clothesType = (TextView)view.findViewById(R.id.clothes_type_tv);
            note = (TextView)view.findViewById(R.id.clothes_note_tv);
            image = (ImageView) view.findViewById(R.id.clothes_image);
            deleteBtn = (ImageView) view.findViewById(R.id.delete_item_btn);

        }
    }

    private void deleteItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


}
