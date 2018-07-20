package com.necer.imagepicker.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.necer.imagepicker.R;
import com.necer.imagepicker.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class MultipleMediaAdapter extends NAdapter<Item> {


    private List<Item> selectItemList;

    public MultipleMediaAdapter(Context context, int layoutId, OnItemClickListener onItemClickListener) {
        super(context, layoutId, onItemClickListener);

        selectItemList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(NViewHolder holder, Item mediaItem, int position) {

        ImageView imageView = holder.getView(R.id.media_thumbnail);
        ImageView sign = holder.getView(R.id.iv_sign);
        Glide.with(mContext).load(mediaItem.uri).into(imageView);

        sign.setImageResource(selectItemList.contains(mediaItem) ? R.drawable.selected:R.drawable.unselected);

    }

    public List<Item> getSelectItemList() {
        return selectItemList;
    }

    public void setSelectItem(Item item) {
        if (selectItemList.contains(item)) {
            selectItemList.remove(item);
        } else {
            selectItemList.add(item);
        }
        notifyDataSetChanged();
    }

}
