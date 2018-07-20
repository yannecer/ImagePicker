package com.necer.imagepicker.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.necer.imagepicker.R;
import com.necer.imagepicker.adapter.MultipleMediaAdapter;
import com.necer.imagepicker.adapter.NAdapter;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.widget.SpacesItemDecoration;

import java.util.List;

/**
 * 多张图片
 */
public class MultipleFragment extends BaseImageFragment implements NAdapter.OnItemClickListener<Item> {

    private RecyclerView recycleView;
    private MultipleMediaAdapter multipleMediaAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_media;
    }

    @Override
    protected void initView(View view) {
        recycleView = view.findViewById(R.id.recycle_view);

        multipleMediaAdapter = new MultipleMediaAdapter(getContext(), R.layout.item_multiple, this);

        recycleView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recycleView.setHasFixedSize(true);
        recycleView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.media_grid_spacing)));
        recycleView.setAdapter(multipleMediaAdapter);

    }

    @Override
    protected void setMediaList(List<Item> mediaItemList) {
        multipleMediaAdapter.replaceData(mediaItemList);
    }

    @Override
    protected boolean isComplete() {
        return multipleMediaAdapter.getSelectItemList().size() != 0;
    }

    @Override
    protected List<Item> getSelectItem() {
        return multipleMediaAdapter.getSelectItemList();
    }

    @Override
    protected String getNotCompleteMessage() {
        return "请选择图片";
    }

    @Override
    public void onItemClick(View itemView, Item mediaItem, int position) {
        multipleMediaAdapter.setSelectItem(mediaItem);
    }
}
