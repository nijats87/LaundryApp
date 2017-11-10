package com.start.laundryapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.start.laundryapp.ClothesActivity;
import com.start.laundryapp.EditClothesActivity;
import com.start.laundryapp.R;
import com.start.laundryapp.models.EditClothesModel;

import java.util.ArrayList;
import java.util.List;


public class ClothesRecyclerAdapter extends BaseRecyclerAdapter<EditClothesModel, ClothesRecyclerAdapter.ViewHolder> {

    public ClothesRecyclerAdapter(Activity context, OnClickListener<EditClothesModel> listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(context).inflate(R.layout.rl_clothes_rv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindModel(ViewHolder holder, EditClothesModel model, final int position) {
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
                        if (data.isEmpty()) {
                            View b = context.findViewById(R.id.confirmClothesBtn);
                            b.setVisibility(View.GONE);
                        }

                    }
                }).setNegativeButton("Xeyr", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                final AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView clothesType, note;
        ImageView image, deleteBtn;

        ViewHolder(View view) {
            super(view);
            clothesType = view.findViewById(R.id.clothes_type_tv);
            note = view.findViewById(R.id.clothes_note_tv);
            image = view.findViewById(R.id.clothes_image);
            deleteBtn = view.findViewById(R.id.delete_item_btn);
        }
    }

    private void deleteItem(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }
}
