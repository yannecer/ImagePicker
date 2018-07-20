package com.necer.imagepicker.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.necer.imagepicker.R;
import com.necer.imagepicker.entity.Item;

import java.util.List;

public class IndicateMediaAdapter extends NAdapter<Item> {

    private int selectIndex;//选中的框
    private OnSelectIndexlistener onSelectIndexlistener;

    public IndicateMediaAdapter(Context context, List<Item> list, int layoutId, OnItemClickListener onItemClickListener,OnSelectIndexlistener onSelectIndexlistener) {
        super(context, list, layoutId, onItemClickListener);
        this.onSelectIndexlistener = onSelectIndexlistener;
    }

    @Override
    public void onBindViewHolder(NViewHolder holder, Item indicateItem, int position) {

        TextView nameView = holder.getView(R.id.tv_name);
        FrameLayout flBg = holder.getView(R.id.fl_bg);
        ImageView imageView = holder.getView(R.id.iv_iamge);

        String name = indicateItem.isRequired ? ("* " + indicateItem.name) : indicateItem.name;

        if (name.contains("*")) {
            SpannableStringBuilder builder = new SpannableStringBuilder(name);
            ForegroundColorSpan textColor = new ForegroundColorSpan(Color.parseColor("#f02a2a"));
            builder.setSpan(textColor, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            nameView.setText(builder);
        } else {
            nameView.setText(name);
        }

        if (selectIndex == position) {
            flBg.setBackgroundResource(R.drawable.bg_image_select);
        } else {
            flBg.setBackgroundResource(R.drawable.bg_image_unselect);
        }

        if (indicateItem.uri != null) {
            Glide.with(mContext).load(indicateItem.uri).into(imageView);
        } else {
            Glide.with(mContext).load(R.drawable.default_image).into(imageView);
        }

    }


    public void setSelectIndicate(Item mediaItem) {
        //置换一下，吧列表中的Item的uri替换成相册中uri
        Item item = mList.get(selectIndex);
        item.uri = mediaItem.uri;

        if (selectIndex < mList.size()-1) {
            selectIndex++;
        }

        if (onSelectIndexlistener != null) {
            onSelectIndexlistener.onSelectIndex(selectIndex);
        }
        notifyDataSetChanged();
    }

    public void setSelectIndex(int position) {
        selectIndex = position;
        if (onSelectIndexlistener != null) {
            onSelectIndexlistener.onSelectIndex(selectIndex);
        }
        notifyDataSetChanged();
    }


    public interface OnSelectIndexlistener{
        void onSelectIndex(int position);
    }


    public boolean isComplete() {
        boolean isComplete = true;
        for (Item item : mList) {
            if (item.isRequired && item.uri == null) {
                isComplete = false;
                break;
            }
        }
        return isComplete;
    }

    public List<Item> getSelectItem() {
        return mList;
    }
}
