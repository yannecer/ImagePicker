package com.necer.imagepicker.ui;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.necer.imagepicker.R;
import com.necer.imagepicker.adapter.AlbumMediaAdapter;
import com.necer.imagepicker.adapter.IndicateMediaAdapter;
import com.necer.imagepicker.adapter.NAdapter;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量上传
 */
public class BatchFragment extends BaseImageFragment implements NAdapter.OnItemClickListener, IndicateMediaAdapter.OnSelectIndexlistener {

    private AlbumMediaAdapter mediaAdapter;
    private IndicateMediaAdapter indicateMediaAdapter;
    private RecyclerView imageRecyclerView;
    private RecyclerView indicateRecycleView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bath_image;
    }

    @Override
    protected void initView(View view) {

        ArrayList<Item> indicateItemList = (ArrayList<Item>) getArguments().getSerializable("indicateItemList");

        indicateRecycleView = view.findViewById(R.id.recycle_indicate);
        indicateRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        indicateRecycleView.setHasFixedSize(true);
        indicateMediaAdapter = new IndicateMediaAdapter(getContext(), indicateItemList, R.layout.item_indicate, this, this);
        indicateRecycleView.setAdapter(indicateMediaAdapter);

        imageRecyclerView = view.findViewById(R.id.recycle_image);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        imageRecyclerView.setHasFixedSize(true);
        imageRecyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.media_grid_spacing)));
        mediaAdapter = new AlbumMediaAdapter(getContext(), R.layout.item_media, this);
        imageRecyclerView.setAdapter(mediaAdapter);
    }

    @Override
    protected void setMediaList(List<Item> mediaItemList) {
        mediaAdapter.replaceData(mediaItemList);
    }

    public static BatchFragment newInstance(ArrayList<Item> indicateItemList) {
        BatchFragment fragment = new BatchFragment();
        Bundle args = new Bundle();
        args.putSerializable("indicateItemList", indicateItemList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onItemClick(View itemView, Object object, int position) {

        if (object instanceof Item) {
            Item Item = (Item) object;
            indicateMediaAdapter.setSelectIndicate(Item);
        } else {
            indicateMediaAdapter.setSelectIndex(position);
        }
    }


    @Override
    public void onSelectIndex(int position) {
        indicateRecycleView.getLayoutManager().scrollToPosition(position);
    }

    public List<Item> getSelectItem() {
        return indicateMediaAdapter.getSelectItem();
    }

    @Override
    protected String getNotCompleteMessage() {
        return "必填项不能为空";
    }

    //必填是否完整
    public boolean isComplete() {
        return indicateMediaAdapter.isComplete();
    }
}
