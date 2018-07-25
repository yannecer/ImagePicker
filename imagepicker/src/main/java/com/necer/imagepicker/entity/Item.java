package com.necer.imagepicker.entity;

import android.net.Uri;

import java.io.Serializable;

/**
 * 上方图片 item
 */
public class Item implements Serializable {


    public int id;
    public String name;
    public boolean isRequired;
    public Uri uri;

    public boolean cameraEnable;


    public Item(int id, String name, boolean isRequired) {
        this.id = id;
        this.name = name;
        this.isRequired = isRequired;
    }

    public Item(Uri uri) {
        this.uri = uri;
    }

    public Item(boolean cameraEnable) {
        this.cameraEnable = cameraEnable;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }

        Item item = (Item) obj;
        return uri.toString().equals(item.uri.toString());
    }

}
