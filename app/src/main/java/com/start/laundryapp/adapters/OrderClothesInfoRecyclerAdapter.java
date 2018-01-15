package com.start.laundryapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.start.laundryapp.Api;
import com.start.laundryapp.HomeActivity;
import com.start.laundryapp.ImageViewerForOrder;
import com.start.laundryapp.R;
import com.start.laundryapp.models.ClothesModel;
import com.start.laundryapp.models.ClothesTypeModel;


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
        for (ClothesTypeModel typeModel : HomeActivity.clothesNames) {
            if (model.clothesTypeId == typeModel.getId()) {
                holder.orderClothesName.setText(typeModel.getNameAz());
                break;
            }
        }
        final String uri = Api.BASE_URL + model.clothesImageUrl;
        ImageLoader.getInstance().displayImage(uri, holder.orderClothesPhoto);


        holder.orderClothesPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ImageViewerForOrder.class);
                intent.putExtra("imageUri", uri);
                context.startActivity(intent);
            }
        });
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
