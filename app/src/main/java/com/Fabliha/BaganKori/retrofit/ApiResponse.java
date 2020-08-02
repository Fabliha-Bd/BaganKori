package com.Fabliha.BaganKori.retrofit;

import java.util.List;

public class ApiResponse
{
    private List<ResponseItem> response;
    public void setResponse(List<ResponseItem> response){
        this.response = response;
    }
    public List<ResponseItem> getResponse(){
        return this.response;
    }
}