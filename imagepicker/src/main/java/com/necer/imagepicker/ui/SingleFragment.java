package com.necer.imagepicker.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.necer.imagepicker.R;
import com.necer.imagepicker.adapter.AlbumMediaAdapter;
import com.necer.imagepicker.adapter.NAdapter;
import com.necer.imagepicker.entity.CaptureStrategy;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.utils.PathUtils;
import com.necer.imagepicker.widget.SpacesItemDecoration;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SingleFragment extends BaseImageFragment implements NAdapter.OnItemClickListener<Item> {

    private RecyclerView recyclerView;
    private AlbumMediaAdapter mediaAdapter;
    private CaptureStrategy mCaptureStrategy;
    private boolean cameraEnable;


    private Uri mCurrentPhotoUri;
    private String mCurrentPhotoPath;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_media;
    }

    @Override
    protected void initView(View view) {
        recyclerView = view.findViewById(R.id.recycle_view);

        mCaptureStrategy = (CaptureStrategy) getArguments().getSerializable("captureStrategy");
        cameraEnable = getArguments().getBoolean("cameraEnable");

        if (cameraEnable && mCaptureStrategy == null) {
            throw new RuntimeException("拍照需要传入CaptureStrategy");
        }

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(getResources().getDimensionPixelSize(R.dimen.media_grid_spacing)));
        mediaAdapter = new AlbumMediaAdapter(getContext(), R.layout.item_media, this);
        recyclerView.setAdapter(mediaAdapter);
    }


    public static SingleFragment newInstance(boolean cameraEnable,CaptureStrategy captureStrategy) {
        SingleFragment fragment = new SingleFragment();
        Bundle args = new Bundle();
        args.putSerializable("captureStrategy", captureStrategy);
        args.putBoolean("cameraEnable", cameraEnable);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    protected void setMediaList(List<Item> mediaItemList) {
        if (cameraEnable) {
            mediaItemList.add(0, new Item(true));
        }
        mediaAdapter.replaceData(mediaItemList);
    }

    @Override
    protected boolean isComplete() {
        return false;
    }

    @Override
    protected ArrayList<Item> getSelectItem() {
        return null;
    }

    @Override
    protected String getNotCompleteMessage() {
        return null;
    }

    @Override
    public void onItemClick(View itemView, Item item, int position) {


        if (item.cameraEnable) {
            //拍照
            dispatchCaptureIntent(getContext(), 222);
        } else if (onSingleSelectItemListener != null) {
            onSingleSelectItemListener.onSingleSelectItem(PathUtils.getPath(getContext(),item.uri));
        }



    }

    public void dispatchCaptureIntent(Context context, int requestCode) {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (captureIntent.resolveActivity(context.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                mCurrentPhotoUri = FileProvider.getUriForFile(getContext(),
                        mCaptureStrategy.authority, photoFile);
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCurrentPhotoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    List<ResolveInfo> resInfoList = context.getPackageManager()
                            .queryIntentActivities(captureIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        context.grantUriPermission(packageName, mCurrentPhotoUri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                }

                startActivityForResult(captureIntent, requestCode);
                /*if (mFragment != null) {
                    mFragment.get().startActivityForResult(captureIntent, requestCode);
                } else {
                    mContext.get().startActivityForResult(captureIntent, requestCode);
                }*/
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir;
        if (mCaptureStrategy.isPublic) {
            storageDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
        } else {
            storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        }

        // Avoid joining path components manually
        File tempFile = new File(storageDir, imageFileName);

        // Handle the situation that user's external storage is not ready
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }

        return tempFile;
    }


    private OnSingleSelectItemListener onSingleSelectItemListener;

    public interface OnSingleSelectItemListener {
        void onSingleSelectItem(String imagePath);
    }

    public SingleFragment setOnSingleSelectItemListener(OnSingleSelectItemListener onSingleSelectItemListener) {
        this.onSingleSelectItemListener = onSingleSelectItemListener;
        return this;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222 && resultCode == Activity.RESULT_OK && onSingleSelectItemListener != null) {
            onSingleSelectItemListener.onSingleSelectItem(mCurrentPhotoPath);
        }
    }
}
