package com.mrtecks.yocreditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {

    Button login;
    EditText phone;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.button);
        phone = findViewById(R.id.editText);
        progress = findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String p = phone.getText().toString();

                if (p.length() == 10)
                {

                    progress.setVisibility(View.VISIBLE);

                    Bean b = (Bean) getApplicationContext();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseurl)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                    Call<loginBean> call = cr.login(p , SharePreferenceUtils.getInstance().getString("token"));
                    call.enqueue(new Callback<loginBean>() {
                        @Override
                        public void onResponse(Call<loginBean> call, Response<loginBean> response) {

                            if (response.body().getStatus().equals("1"))
                            {

                                Intent intent = new Intent(Login.this , OTP.class);
                                SharePreferenceUtils.getInstance().saveString("phone" , response.body().getPhone());
                                //SharePreferenceUtils.getInstance().saveString("id" , response.body().getMessage());
                                startActivity(intent);
                                Toast.makeText(Login.this, "Please verify your phone number", Toast.LENGTH_SHORT).show();
                                finish();

                            }
                            else
                            {
                                Toast.makeText(Login.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            progress.setVisibility(View.GONE);

                        }

                        @Override
                        public void onFailure(Call<loginBean> call, Throwable t) {
                            progress.setVisibility(View.GONE);
                            t.printStackTrace();
                        }
                    });

                }
                else
                {
                    Toast.makeText(Login.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
