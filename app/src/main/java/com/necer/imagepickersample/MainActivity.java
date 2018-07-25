package com.necer.imagepickersample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.necer.imagepicker.MyLog;
import com.necer.imagepicker.NImage;
import com.necer.imagepicker.entity.CaptureStrategy;
import com.necer.imagepicker.entity.Item;
import com.necer.imagepicker.entity.SelectType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void aaa(View view) {
        // startActivity(new Intent(this,ImageActivity.class));


        ArrayList<Item> indicateItems = new ArrayList<>();
        indicateItems.add(new Item(0, "车身", true));
        indicateItems.add(new Item(1, "车身钱", true));
        indicateItems.add(new Item(2, "车身后", false));
        indicateItems.add(new Item(3, "车身22", true));
        indicateItems.add(new Item(4, "车身1", false));
        indicateItems.add(new Item(5, "车身2", false));
        indicateItems.add(new Item(6, "车身3", true));
        indicateItems.add(new Item(7, "车身4", false));
        indicateItems.add(new Item(8, "车身5", false));


        //单选
        NImage.from(this).select(SelectType.SINGLE).capture(true).captureStrategy(new CaptureStrategy(true, "com.necer.imagepickersample.fileprovider"))
                .forResult(300);

      // 批量 NImage.from(this).select(SelectType.BATCH).setIndicateItems(indicateItems).forResult(300);
      // 多选 NImage.from(this).select(SelectType.MULTIPLE).maxCount(4).forResult(300);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 300 && data != null) {
            String singleImage = NImage.getSingleImage(data);

            List<String> multipleImages = NImage.getMultipleImages(data);


            MyLog.d("item:::单独：：" + singleImage);


            if (multipleImages != null) {
                for (String item : multipleImages) {
                    MyLog.d("item::多：：" + item);
                }
            }


        }


    }
}
