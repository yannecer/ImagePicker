package com.necer.imagepicker.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.necer.imagepicker.entity.Album;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.entity.MediaItem;
import com.necer.imagepicker.model.AlbumMediaCollection;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseImageFragment extends Fragment {

    private AlbumMediaCollection mAlbumMediaCollection = new AlbumMediaCollection();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

        mAlbumMediaCollection.onCreate(getActivity(), new AlbumMediaCollection.AlbumMediaCallbacks() {
            @Override
            public void onAlbumMediaLoad(Cursor cursor) {
                List<MediaItem> itemList = new ArrayList<>();
                for (int i = 0; i < cursor.getCount(); i++) {
                    boolean b = cursor.moveToPosition(i);
                    if (b && isDataValid(cursor)) {
                        itemList.add(MediaItem.valueOf(cursor));
                    }
                }
                setMediaList(itemList);
            }
        });
    }

    protected void upAlbum(Album album) {
        mAlbumMediaCollection.load(album, false);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAlbumMediaCollection.onDestroy();
    }

    protected boolean isDataValid(Cursor cursor) {
        return cursor != null && !cursor.isClosed();
    }


    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void setMediaList(List<MediaItem> mediaItemList);

    protected abstract boolean isComplete();

    protected abstract List<Item> getSelectItem();

    protected abstract String getNotCompleteMessage();

}
