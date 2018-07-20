package com.necer.imagepicker.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.necer.imagepicker.R;
import com.necer.imagepicker.adapter.AlbumMediaAdapter;
import com.necer.imagepicker.adapter.NAdapter;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.entity.MediaItem;
import com.necer.imagepicker.widget.SpacesItemDecoration;

import java.util.List;

public class SingleFragment extends BaseImageFragment implements NAdapter.OnItemClickListener<MediaItem> {

    private RecyclerView recyclerView;
    private AlbumMediaAdapter mediaAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_media;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.media_grid_spacing)));
        mediaAdapter = new AlbumMediaAdapter(getContext(), R.layout.item_media, this);
        recyclerView.setAdapter(mediaAdapter);
    }

    @Override
    protected void setMediaList(List<Item> mediaItemList) {
        mediaAdapter.replaceData(mediaItemList);
    }


    @Override
    protected boolean isComplete() {
        return false;
    }

    @Override
    protected List<Item> getSelectItem() {
        return null;
    }

    @Override
    protected String getNotCompleteMessage() {
        return null;
    }

    @Override
    public void onItemClick(View itemView, MediaItem mediaItem, int position) {

        if (onSingleSelectItemListener != null) {
            onSingleSelectItemListener.onSingleSelectItem(mediaItem);
        }

    }


    private OnSingleSelectItemListener onSingleSelectItemListener;

    public interface OnSingleSelectItemListener{
        void onSingleSelectItem(MediaItem mediaItem);
    }

    public SingleFragment setOnSingleSelectItemListener(OnSingleSelectItemListener onSingleSelectItemListener) {
        this.onSingleSelectItemListener = onSingleSelectItemListener;
        return this;
    }

}
