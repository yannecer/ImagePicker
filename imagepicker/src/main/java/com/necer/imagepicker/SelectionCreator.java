package com.necer.imagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.necer.imagepicker.entity.CaptureStrategy;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.entity.SelectType;
import com.necer.imagepicker.ui.ImageActivity;

import java.util.ArrayList;

public class SelectionCreator {

    private SelectType selectType;
    private ArrayList<Item> indicateItems;
    private boolean cameraEnable;
    private CaptureStrategy captureStrategy;
    private int maxCount = 9;

    private NImage nImage;

    public SelectionCreator(NImage nImage,SelectType selectType) {
        this.selectType = selectType;
        this.nImage = nImage;
    }

    public SelectionCreator setIndicateItems(ArrayList<Item> indicateItems) {
        this.indicateItems = indicateItems;
        return this;
    }

    public SelectionCreator maxCount(int maxCount) {
        this.maxCount = maxCount;
        return this;
    }


    public SelectionCreator capture(boolean cameraEnable) {
        this.cameraEnable = cameraEnable;
        return this;
    }

    public SelectionCreator captureStrategy(CaptureStrategy captureStrategy) {
        this.captureStrategy = captureStrategy;
        return this;
    }


    public void forResult(int requestCode) {
        Activity activity = nImage.getActivity();
        if (activity == null) {
            return;
        }

        Intent intent = getImageIntent(activity);

        Fragment fragment = nImage.getFragment();
        if (fragment != null) {
            fragment.startActivityForResult(intent, requestCode);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }


    private Intent getImageIntent(Context context) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra("selectType", selectType);
        intent.putExtra("indicateItems", indicateItems);
        intent.putExtra("maxCount", maxCount);
        intent.putExtra("cameraEnable", cameraEnable);
        intent.putExtra("captureStrategy", captureStrategy);

        return intent;
    }
}
