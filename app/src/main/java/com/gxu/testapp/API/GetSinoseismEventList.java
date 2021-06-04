package com.gxu.testapp.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetSinoseismEventList {

    @GET("/api/home/sinoseism/event_filter")
    Call<ResponseBody> downloadEvenList();
}
