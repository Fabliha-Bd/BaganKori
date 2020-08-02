package com.Fabliha.BaganKori.retrofit;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("response")
	private List<ResponseItem> response;

	public List<ResponseItem> getResponse(){
		return response;
	}
}