package com.necer.imagepickersample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.necer.imagepicker.NImage;
import com.necer.imagepicker.entity.Item;

import java.util.ArrayList;

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



        NImage.toSelect(this,indicateItems, 100);
    }
}
