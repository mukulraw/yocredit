package com.mrtecks.yocreditapp;


import com.mrtecks.yocreditapp.contactPOJO.contactBean;
import com.mrtecks.yocreditapp.loanDetailsPOJO.loanDetailsBean;
import com.mrtecks.yocreditapp.statusPOJO.statusBean;
import com.mrtecks.yocreditapp.updatePOJO.updateBean;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

;

public interface AllApiIneterface {

    @Multipart
    @POST("api/login.php")
    Call<loginBean> login(
            @Part("phone") String client,
            @Part("token") String token
    );

    @Multipart
    @POST("api/verify.php")
    Call<updateBean> verify(
            @Part("phone") String client,
            @Part("otp") String otp
    );

    @Multipart
    @POST("api/update_basic.php")
    Call<updateBean> update_basic(
            @Part("user_id") String user_id,
            @Part("name") String name,
            @Part("pin") String pin,
            @Part MultipartBody.Part file3
    );

    @Multipart
    @POST("api/update_personal.php")
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
    @POST("api/update_document.php")
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
    @POST("api/getStatus.php")
    Call<statusBean> getStatus(
            @Part("user_id") String user_id
    );

    @Multipart
    @POST("api/applyLoan.php")
    Call<statusBean> applyLoan(
            @Part("user_id") String user_id,
            @Part("amount") String amount,
            @Part("interest") String interest,
            @Part("tenover") String tenover
    );

    @Multipart
    @POST("api/getLoanDetails.php")
    Call<loanDetailsBean> getLoanDetails(
            @Part("user_id") String user_id,
            @Part("id") String id
    );

    @Multipart
    @POST("api/payEMI.php")
    Call<updateBean> payEMI(
            @Part("user_id") String user_id,
            @Part("loan_id") String loan_id,
            @Part("amount") String amount,
            @Part MultipartBody.Part file3
    );

    @Multipart
    @POST("api/getLoans.php")
    Call<statusBean> getLoans(
            @Part("user_id") String user_id
    );

    @GET("api/getContact.php")
    Call<contactBean> getContact();

    @Multipart
    @POST("api/getNotification.php")
    Call<List<notiBean>> getNoti(
            @Part("id") String id
    );

}
