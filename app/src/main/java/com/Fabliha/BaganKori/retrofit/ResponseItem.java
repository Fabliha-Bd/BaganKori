package com.Fabliha.BaganKori.retrofit;

import com.google.gson.annotations.SerializedName;

public class ResponseItem{

	@SerializedName("mPrice")
	private String mPrice;

	@SerializedName("quantityAvailable")
	private int quantityAvailable;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;

	public String getMPrice(){
		return mPrice;
	}

	public int getQuantityAvailable(){
		return quantityAvailable;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getName(){
		return name;
	}

	public String getType(){
		return type;
	}
}