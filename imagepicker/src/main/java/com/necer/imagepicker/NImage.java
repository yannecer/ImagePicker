package com.necer.imagepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.necer.imagepicker.entity.SelectType;

import java.lang.ref.WeakReference;
import java.util.List;

public final class NImage {

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;

    private NImage(Activity activity) {
        this(activity, null);
    }

    private NImage(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private NImage(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }


    public static NImage from(Activity activity) {
        return new NImage(activity);
    }


    public static NImage from(Fragment fragment) {
        return new NImage(fragment);
    }

    public SelectionCreator select(SelectType selectType) {
        return new SelectionCreator(this,selectType);
    }


    @Nullable
    Activity getActivity() {
        return mContext.get();
    }

    @Nullable
    Fragment getFragment() {
        return mFragment != null ? mFragment.get() : null;
    }


    public static String getSingleImage(Intent intent) {
        return intent.getStringExtra("single");
    }

    public static List<String> getMultipleImages(Intent intent) {
        return intent.getStringArrayListExtra("multiple");
    }

}
