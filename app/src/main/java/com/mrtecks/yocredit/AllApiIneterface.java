package com.mrtecks.yocredit;


import com.mrtecks.yocredit.statusPOJO.statusBean;
import com.mrtecks.yocredit.updatePOJO.updateBean;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

;

public interface AllApiIneterface {

    @Multipart
    @POST("yocredit/api/login.php")
    Call<loginBean> login(
            @Part("phone") String client,
            @Part("token") String token
    );

    @Multipart
    @POST("yocredit/api/verify.php")
    Call<loginBean> verify(
            @Part("phone") String client,
            @Part("otp") String otp
    );

    @Multipart
    @POST("yocredit/api/update_basic.php")
    Call<updateBean> update_basic(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("pin") String pin,
            @Part MultipartBody.Part file3
    );

    @Multipart
    @POST("yocredit/api/update_personal.php")
    Call<updateBean> update_personal(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("dob") String dob,
            @Part("father") String father,
            @Part("mother") String mother,
            @Part("address") String address,
            @Part("income") String income,
            @Part("reference1") String reference1,
            @Part("reference2") String reference2
    );

    @Multipart
    @POST("yocredit/api/update_document.php")
    Call<updateBean> update_document(
            @Part("user_id") String user_id,
            @Part("amount") String amount,
            @Part("tenover") String tenover,
            @Part MultipartBody.Part file1,
            @Part MultipartBody.Part file2,
            @Part MultipartBody.Part file3,
            @Part MultipartBody.Part file4
    );

    @Multipart
    @POST("yocredit/api/getStatus.php")
    Call<statusBean> getStatus(
            @Part("user_id") String user_id
    );

}