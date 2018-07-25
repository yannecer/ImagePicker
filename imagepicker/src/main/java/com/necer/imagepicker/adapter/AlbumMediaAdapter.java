package com.necer.imagepicker.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.necer.imagepicker.R;
import com.necer.imagepicker.entity.Item;

public class AlbumMediaAdapter extends NAdapter<Item> {
    public AlbumMediaAdapter(Context context, int layoutId, OnItemClickListener onItemClickListener) {
        super(context, layoutId, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(NViewHolder holder, Item item, int position) {

        ImageView imageView = holder.getView(R.id.media_thumbnail);
        ImageView camera = holder.getView(R.id.iv_camera);

        imageView.setVisibility(item.cameraEnable ? View.GONE : View.VISIBLE);
        camera.setVisibility(item.cameraEnable ? View.VISIBLE : View.GONE);

        if (imageView.getVisibility() == View.VISIBLE && item.uri != null) {
            Glide.with(mContext).load(item.uri).into(imageView);
        }

    }
}
