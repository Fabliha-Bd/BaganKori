package com.Fabliha.BaganKori.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface IJsonPlaceHolderAPI {


    @GET
    Call<ApiResponse> getProducts(@Url String param);
}
