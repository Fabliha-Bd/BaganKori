package com.Fabliha.BaganKori;

import com.Fabliha.BaganKori.shop.ProductType;

public interface Product {
    String getName();
    float getPrice();
    Company getCompanyInfo();
    int getAvailableUnit();
    ProductType getType();
}
