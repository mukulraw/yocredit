package com.mrtecks.yocredit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mrtecks.yocredit.updatePOJO.Data;
import com.mrtecks.yocredit.updatePOJO.updateBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Personal extends AppCompatActivity {
    Button submit;

    EditText name , dob , father , mother , address , income , reference1 , reference2;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        submit = findViewById(R.id.submit);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        father = findViewById(R.id.father);
        mother = findViewById(R.id.mother);
        address = findViewById(R.id.address);
        income = findViewById(R.id.income);
        reference1 = findViewById(R.id.reference1);
        reference2 = findViewById(R.id.reference2);
        progress = findViewById(R.id.progressBar);

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(Personal.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.date_dialog);
                dialog.show();


                final DatePicker picker = dialog.findViewById(R.id.date);
                Button ok = dialog.findViewById(R.id.ok);

                long now = System.currentTimeMillis() - 1000;
                picker.setMaxDate(now);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int year = picker.getYear();
                        int month = picker.getMonth();
                        int day = picker.getDayOfMonth();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);

                        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-YYYY");
                        String strDate = format.format(calendar.getTime());

                        dob.setText(strDate);

                        dialog.dismiss();

                    }
                });

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String n = name.getText().toString();
                String d = dob.getText().toString();
                String f = father.getText().toString();
                String m = mother.getText().toString();
                String a = address.getText().toString();
                String i = income.getText().toString();
                String r1 = reference1.getText().toString();
                String r2 = reference2.getText().toString();

                if (n.length() > 0)
                {
                    if (d.length() > 0)
                    {
                        if (f.length() > 0)
                        {
                            if (m.length() > 0)
                            {
                                if (a.length() > 0)
                                {
                                    if (i.length() > 0)
                                    {

                                        progress.setVisibility(View.VISIBLE);

                                        Bean b = (Bean) getApplicationContext();

                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseurl)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();

                                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                        Call<updateBean> call = cr.update_personal(
                                                SharePreferenceUtils.getInstance().getString("id") ,
                                                n ,
                                                d ,
                                                f ,
                                                m ,
                                                a ,
                                                i ,
                                                r1 ,
                                                r2
                                        );


                                        call.enqueue(new Callback<updateBean>() {
                                            @Override
                                            public void onResponse(Call<updateBean> call, Response<updateBean> response) {

                                                if (response.body().getStatus().equals("1"))
                                                {
                                                    Data item = response.body().getData();
                                                    SharePreferenceUtils.getInstance().saveString("name" , item.getName());
                                                    SharePreferenceUtils.getInstance().saveString("dob" , item.getDob());
                                                    SharePreferenceUtils.getInstance().saveString("father" , item.getFather());
                                                    SharePreferenceUtils.getInstance().saveString("mother" , item.getMother());
                                                    SharePreferenceUtils.getInstance().saveString("address" , item.getAddress());
                                                    SharePreferenceUtils.getInstance().saveString("income" , item.getIncome());
                                                    SharePreferenceUtils.getInstance().saveString("reference1" , item.getReference1());
                                                    SharePreferenceUtils.getInstance().saveString("reference2" , item.getReference2());
                                                    Toast.makeText(Personal.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(Personal.this , Document.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(Personal.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(Personal.this, "Invalid income", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(Personal.this, "Invalid address", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(Personal.this, "Invalid mother's name", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(Personal.this, "Invalid father's name", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(Personal.this, "Invalid D.O.B.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(Personal.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
