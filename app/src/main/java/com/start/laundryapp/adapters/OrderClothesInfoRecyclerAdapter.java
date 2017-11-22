package com.start.laundryapp.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.start.laundryapp.Api;
import com.start.laundryapp.Home;
import com.start.laundryapp.R;
import com.start.laundryapp.models.ClothesModel;
import com.start.laundryapp.models.ClothesTypeModel;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class OrderClothesInfoRecyclerAdapter extends BaseRecyclerAdapter<ClothesModel, OrderClothesInfoRecyclerAdapter.ViewHolder> {

    public OrderClothesInfoRecyclerAdapter(Activity context, OnClickListener<ClothesModel> listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.rl_orderclothesinfo_rv, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindModel(ViewHolder holder, ClothesModel model, int position) {
        holder.orderClothesNote.setText(model.notes);
        for (ClothesTypeModel typeModel : Home.clothesNames) {
            if (model.clothesTypeId == typeModel.getId()) {
                holder.orderClothesName.setText(typeModel.getNameAz());
                break;
            }
        }
        String uri = Api.BASE_URL + model.clothesImageUrl;
        ImageLoader.getInstance().displayImage(uri, holder.orderClothesPhoto);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView orderClothesPhoto;
        TextView orderClothesName, orderClothesNote;

        ViewHolder(View view) {
            super(view);
            orderClothesPhoto = view.findViewById(R.id.orderClothesPhoto);
            orderClothesName = view.findViewById(R.id.orderClothesName);
            orderClothesNote = view.findViewById(R.id.orderClothesNote);

        }
    }
}
