package com.gxu.testapp.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Getpdf {

    @GET("/api/home/resource/download")
    Call<ResponseBody> downloadFile(@Query("fid") int fid);;
}
