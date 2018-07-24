package com.necer.imagepicker;

import android.app.Activity;
import android.content.Intent;

import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.entity.SelectType;
import com.necer.imagepicker.ui.ImageActivity;

import java.util.ArrayList;

public class NImage {


    public static void toSelect(Activity activity, SelectType selectType, int requestCode) {
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra("selectType", selectType);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void toSelect(Activity activity, ArrayList<Item> itemList, int requestCode) {
        Intent intent = new Intent(activity, ImageActivity.class);
        intent.putExtra("selectType", SelectType.BATCH);
        intent.putExtra("itemList", itemList);
        activity.startActivityForResult(intent, requestCode);
    }


}
