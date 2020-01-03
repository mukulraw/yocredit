package com.mrtecks.yocreditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mrtecks.yocreditapp.updatePOJO.Data;
import com.mrtecks.yocreditapp.updatePOJO.updateBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OTP extends AppCompatActivity {
    Button login;
    EditText otp;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        login = findViewById(R.id.button);
        otp = findViewById(R.id.editText);
        progress = findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String o = otp.getText().toString();

                if (o.length() > 0)
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<updateBean> call = cr.verify(SharePreferenceUtils.getInstance().getString("phone") , o);
                    call.enqueue(new Callback<updateBean>() {
                        @Override
                        public void onResponse(Call<updateBean> call, Response<updateBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                Data item = response.body().getData();
                                SharePreferenceUtils.getInstance().saveString("phone" , item.getPhone());
                                SharePreferenceUtils.getInstance().saveString("id" , item.getUserId());
                                SharePreferenceUtils.getInstance().saveString("name" , item.getName());
                                SharePreferenceUtils.getInstance().saveString("photo" , item.getPhoto());
                                SharePreferenceUtils.getInstance().saveString("pin" , item.getPin());
                                SharePreferenceUtils.getInstance().saveString("name" , item.getName());
                                SharePreferenceUtils.getInstance().saveString("dob" , item.getDob());
                                SharePreferenceUtils.getInstance().saveString("father" , item.getFather());
                                SharePreferenceUtils.getInstance().saveString("mother" , item.getMother());
                                SharePreferenceUtils.getInstance().saveString("address" , item.getAddress());

                                SharePreferenceUtils.getInstance().saveString("reference1" , item.getReference1());
                                SharePreferenceUtils.getInstance().saveString("reference2" , item.getReference2());
                                SharePreferenceUtils.getInstance().saveString("pan" , item.getPan());
                                SharePreferenceUtils.getInstance().saveString("aadhar1" , item.getAadhar1());
                                SharePreferenceUtils.getInstance().saveString("aadhar2" , item.getAadhar2());
                                SharePreferenceUtils.getInstance().saveString("passbook" , item.getPassbook());
                                SharePreferenceUtils.getInstance().saveString("amount" , item.getAmount());
                                SharePreferenceUtils.getInstance().saveString("tenover" , item.getTenover());
                                SharePreferenceUtils.getInstance().saveString("income" , item.getIncome());

                                if (SharePreferenceUtils.getInstance().getString("pan").length() > 0)
                                {
                                    Intent intent = new Intent(OTP.this , MainActivity.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }
                                else
                                {
                                    Intent intent = new Intent(OTP.this , Steps.class);
                                    startActivity(intent);
                                    finishAffinity();
                                }



                                Toast.makeText(OTP.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(OTP.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<updateBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                        }
                    });

                }
                else
                {
                    Toast.makeText(OTP.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}
