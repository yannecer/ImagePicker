package com.necer.imagepicker.entity;

import java.io.Serializable;

/**
 * 上方图片 item
 */
public class Item implements Serializable{


    public int id;
    public String name;
    public boolean isRequired;

    public MediaItem mediaItem;


    public Item(int id, String name, boolean isRequired) {
        this.id = id;
        this.name = name;
        this.isRequired = isRequired;
    }
}
