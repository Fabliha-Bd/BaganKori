package com.Fabliha.garden2.ui.shop.seeds.model;

public class Upload {
    private String mName;
    private String mImageUrl;
    private String mPrice;
    private String mType;


    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String price, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mPrice= price;
        mImageUrl = imageUrl;
    }
    public Upload(String name, String price, String imageUrl, String type) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mPrice= price;
        mImageUrl = imageUrl;
        mType=type;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }


    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }
}