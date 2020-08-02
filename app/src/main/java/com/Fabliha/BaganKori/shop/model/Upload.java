package com.Fabliha.BaganKori.shop.model;

public class Upload {

    private String mName;
    private String mImageUrl;
    private String mPrice;
    private String mType;
    private Long mQuantity;


    public Upload() {
        //empty constructor needed
    }

    public Upload(String name, String price, String imageUrl, String type, Long quantity) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mPrice= price;
        mImageUrl = imageUrl;
        mType=type;
        mQuantity=quantity;
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

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public Long getQuantityAvailable() {
        return mQuantity;
    }

    public void setQuantityAvailable(Long quantityAvailable) {
        this.mQuantity = quantityAvailable;
    }

    @Override
    public String toString() {
        return "Upload{" +
                "mName='" + mName + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                ", mPrice='" + mPrice + '\'' +
                ", mType='" + mType + '\'' +
                '}';
    }
}