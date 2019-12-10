package com.mrtecks.yocredit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mrtecks.yocredit.updatePOJO.updateBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Status extends AppCompatActivity {

    TextView status;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        status = findViewById(R.id.textView6);
        progress = findViewById(R.id.progressBar);

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<updateBean> call = cr.getStatus(
                SharePreferenceUtils.getInstance().getString("id")
        );

        call.enqueue(new Callback<updateBean>() {
            @Override
            public void onResponse(Call<updateBean> call, Response<updateBean> response) {

                status.setText("STATUS: " + response.body().getMessage());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<updateBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }
}
