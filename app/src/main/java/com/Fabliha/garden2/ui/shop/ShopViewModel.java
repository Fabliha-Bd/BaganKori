package com.Fabliha.garden2.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShopViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> productType;
    private MutableLiveData<String> categorytext;

    public MutableLiveData<String> getCategorytext() {
        if (categorytext == null) {
            categorytext = new MutableLiveData<String>();
            categorytext.setValue("Bansai");
        }
        return categorytext;
    }

    public void setCategorytext(String text) {
        if (categorytext == null) {
            categorytext = new MutableLiveData<String>();
        }
        categorytext.setValue(text);
    }


    public ShopViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Categories");

    }

    public void setProductType(String type) {
        if (productType == null) {
            productType = new MutableLiveData<String>();
        }
        productType.setValue(type);
    }

    public MutableLiveData<String> getProductType() {
        if (productType == null) {
            productType = new MutableLiveData<String>();
            productType.setValue("bansai");
        }
        return productType;
    }

    public LiveData<String> getText() {
        return mText;
    }

}