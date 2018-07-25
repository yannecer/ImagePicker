package com.necer.imagepicker.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.necer.imagepicker.R;
import com.necer.imagepicker.entity.Item;

import java.util.ArrayList;

public class MultipleMediaAdapter extends NAdapter<Item> {


    private ArrayList<Item> selectItemList;
    private int maxCount;

    public MultipleMediaAdapter(Context context, int layoutId, OnItemClickListener onItemClickListener,int maxCount) {
        super(context, layoutId, onItemClickListener);
        this.maxCount = maxCount;
        selectItemList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(NViewHolder holder, Item mediaItem, int position) {

        ImageView imageView = holder.getView(R.id.media_thumbnail);
        ImageView sign = holder.getView(R.id.iv_sign);
        Glide.with(mContext).load(mediaItem.uri).into(imageView);

        sign.setImageResource(selectItemList.contains(mediaItem) ? R.drawable.selected:R.drawable.unselected);

    }

    public ArrayList<Item> getSelectItemList() {
        return selectItemList;
    }

    public void setSelectItem(Item item) {
        if (selectItemList.contains(item)) {
            selectItemList.remove(item);
        } else {
            if (selectItemList.size() >= maxCount) {
                Toast.makeText(mContext,"最多选择"+maxCount+"张图片",Toast.LENGTH_SHORT).show();
            } else {
                selectItemList.add(item);
            }

        }
        notifyDataSetChanged();
    }

}
