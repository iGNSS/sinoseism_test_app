package com.gxu.testapp.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface getpdflist {
    @GET("/api/home/resource/list")
    Call<ResponseBody> downloadFile();
}
