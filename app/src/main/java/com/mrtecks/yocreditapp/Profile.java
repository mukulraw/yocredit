package com.mrtecks.yocreditapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mrtecks.yocreditapp.contactPOJO.contactBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Profile extends AppCompatActivity {

    Toolbar toolbar;
    TextView phone , email;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.toolbar2);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        progress = findViewById(R.id.progressBar3);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle("Contact Us");

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<contactBean> call = cr.getContact();

        call.enqueue(new Callback<contactBean>() {
            @Override
            public void onResponse(Call<contactBean> call, Response<contactBean> response) {

                phone.setText(response.body().getData().getPhone());
                email.setText(response.body().getData().getEmail());

                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<contactBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }
}
