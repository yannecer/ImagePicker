package com.necer.imagepicker.ui;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.necer.imagepicker.MyLog;
import com.necer.imagepicker.R;
import com.necer.imagepicker.adapter.AlbumsAdapter;
import com.necer.imagepicker.entity.Album;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.entity.MediaItem;
import com.necer.imagepicker.entity.SelectType;
import com.necer.imagepicker.model.AlbumCollection;
import com.necer.imagepicker.utils.StatusbarUI;

import java.util.ArrayList;
import java.util.List;

import static com.necer.imagepicker.entity.SelectType.BATCH;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener, AlbumCollection.AlbumCallbacks, SingleFragment.OnSingleSelectItemListener {

    private static final int MAX_SHOWN_COUNT = 5;
    private ListPopupWindow mListPopupWindow;
    private AlbumsAdapter mAlbumsAdapter;
    private AlbumCollection mAlbumCollection = new AlbumCollection();
    private FrameLayout mContainer;
    private TextView mEmptyView;
    private TextView mSure;

    private BaseImageFragment imageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        StatusbarUI.setStatusBarUIMode(this, Color.parseColor("#ffffff"), true);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) supportActionBar.hide();

        mContainer = findViewById(R.id.fl);
        mEmptyView = findViewById(R.id.tv_empty);
        mSure = findViewById(R.id.tv_sure);

        findViewById(R.id.tv_select_albums).setOnClickListener(this);
        findViewById(R.id.tv_sure).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

        mAlbumCollection.onCreate(this, this);
        mAlbumCollection.onRestoreInstanceState(savedInstanceState);
        mAlbumCollection.loadAlbums();



        SelectType selectType = (SelectType) getIntent().getSerializableExtra("selectType");
        ArrayList<Item> itemList = (ArrayList<Item>) getIntent().getSerializableExtra("itemList");

        if (selectType == BATCH && itemList == null) {
            throw new RuntimeException("批量上传，需要传入Indicate列表！");
        }

        switch (selectType) {
            case BATCH:
                imageFragment = BatchFragment.newInstance(itemList);
                break;
            case MULTIPLE:
                imageFragment = new MultipleFragment();
                break;
            case SINGLE:
                imageFragment = new SingleFragment().setOnSingleSelectItemListener(this);
                break;
            default:
                throw new RuntimeException("请选择选取类型");
        }


        mSure.setVisibility(imageFragment instanceof SingleFragment ? View.GONE : View.VISIBLE);

        getSupportFragmentManager().beginTransaction().replace(R.id.fl, imageFragment, BatchFragment.class.getSimpleName()).commitAllowingStateLoss();

        mListPopupWindow = new ListPopupWindow(this, null, R.attr.listPopupWindowStyle);
        mListPopupWindow.setModal(true);
        float density = getResources().getDisplayMetrics().density;
        mListPopupWindow.setContentWidth((int) (216 * density));
        mListPopupWindow.setAnchorView(findViewById(R.id.tv_select_albums));

        mAlbumsAdapter = new AlbumsAdapter(this, null, false);
        mListPopupWindow.setAdapter(mAlbumsAdapter);
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mAlbumCollection.setStateCurrentSelection(position);
                mAlbumsAdapter.getCursor().moveToPosition(position);
                Album album = Album.valueOf(mAlbumsAdapter.getCursor());
                imageFragment.upAlbum(album);
                mListPopupWindow.dismiss();

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_select_albums) {
            float density = getResources().getDisplayMetrics().density;
            int itemHeight = (int) (70 * density);
            mListPopupWindow.setHeight(mAlbumsAdapter.getCount() > MAX_SHOWN_COUNT ? itemHeight * MAX_SHOWN_COUNT : itemHeight * mAlbumsAdapter.getCount());
            mListPopupWindow.show();
        } else if (id == R.id.iv_back) {
            finish();
        } else if (id == R.id.tv_sure) {
            if (imageFragment.isComplete()) {
                List<Item> selectItem = imageFragment.getSelectItem();
                for (Item item : selectItem) {
                    if (item.uri != null)
                        MyLog.d("item::" + item.uri);
                }
            } else {
                Toast.makeText(this, imageFragment.getNotCompleteMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onAlbumLoad(final Cursor cursor) {
        mAlbumsAdapter.swapCursor(cursor);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                cursor.moveToPosition(mAlbumCollection.getCurrentSelection());
                Album album = Album.valueOf(cursor);

                if (album.isAll() && album.isEmpty()) {
                    mContainer.setVisibility(View.GONE);
                    mEmptyView.setVisibility(View.VISIBLE);
                } else {
                    mContainer.setVisibility(View.VISIBLE);
                    mEmptyView.setVisibility(View.GONE);
                    imageFragment.upAlbum(album);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAlbumCollection.onDestroy();
    }

    //单张的图片选择
    @Override
    public void onSingleSelectItem(MediaItem mediaItem) {
        MyLog.d("mediaItem::::" + mediaItem.uri);
    }
}
