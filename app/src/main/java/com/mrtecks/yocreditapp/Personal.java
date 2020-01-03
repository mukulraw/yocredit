package com.mrtecks.yocreditapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mrtecks.yocreditapp.updatePOJO.Data;
import com.mrtecks.yocreditapp.updatePOJO.updateBean;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Personal extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Button submit;
    Toolbar toolbar;
    EditText name, dob, father, mother, address, income, reference1, reference2;
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

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle("Provide Personal Details");


        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long now = System.currentTimeMillis() - 1000;

                Calendar c = Calendar.getInstance();
//Set time in milliseconds
                c.setTimeInMillis(now);
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int hr = c.get(Calendar.HOUR);
                int min = c.get(Calendar.MINUTE);
                int sec = c.get(Calendar.SECOND);

                new SpinnerDatePickerDialogBuilder()
                        .context(Personal.this)
                        .callback(Personal.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .maxDate(mYear, mMonth, mDay)
                        .defaultDate(mYear, mMonth, mDay)
                        .build()
                        .show();


                /*final Dialog dialog = new Dialog(Personal.this);
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
                });*/

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

                int agg = getAge(d);

                Log.d("year", String.valueOf(agg));

                if (n.length() > 0) {
                    if (d.length() > 0) {

                        if (agg >= 19) {
                            if (f.length() > 0) {
                                if (m.length() > 0) {
                                    if (a.length() > 0) {
                                        if (i.length() > 0) {

                                            progress.setVisibility(View.VISIBLE);

                                            Bean b = (Bean) getApplicationContext();

                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(b.baseurl)
                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();

                                            AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                                            Call<updateBean> call = cr.update_personal(
                                                    SharePreferenceUtils.getInstance().getString("id"),
                                                    n,
                                                    d,
                                                    f,
                                                    m,
                                                    a,
                                                    i,
                                                    r1,
                                                    r2
                                            );


                                            call.enqueue(new Callback<updateBean>() {
                                                @Override
                                                public void onResponse(Call<updateBean> call, Response<updateBean> response) {

                                                    if (response.body().getStatus().equals("1")) {
                                                        Data item = response.body().getData();
                                                        SharePreferenceUtils.getInstance().saveString("name", item.getName());
                                                        SharePreferenceUtils.getInstance().saveString("dob", item.getDob());
                                                        SharePreferenceUtils.getInstance().saveString("father", item.getFather());
                                                        SharePreferenceUtils.getInstance().saveString("mother", item.getMother());
                                                        SharePreferenceUtils.getInstance().saveString("address", item.getAddress());

                                                        SharePreferenceUtils.getInstance().saveString("reference1", item.getReference1());
                                                        SharePreferenceUtils.getInstance().saveString("reference2", item.getReference2());
                                                        Toast.makeText(Personal.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                                        Intent intent = new Intent(Personal.this, Document.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(Personal.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                    progress.setVisibility(View.GONE);

                                                }

                                                @Override
                                                public void onFailure(Call<updateBean> call, Throwable t) {
                                                    progress.setVisibility(View.GONE);
                                                }
                                            });

                                        } else {
                                            Toast.makeText(Personal.this, "Invalid income", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Personal.this, "Invalid address", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Personal.this, "Invalid mother's name", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Personal.this, "Invalid father's name", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Personal.this, "Age must be minimum 19 years", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        Toast.makeText(Personal.this, "Invalid D.O.B.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Personal.this, "Invalid name", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-YYYY");
        String strDate = format.format(calendar.getTime());

        dob.setText(strDate);

    }

    public static int getAge(String date) {

        int age = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-YYYY");
            Date date1 = format.parse(date);
            Calendar now = Calendar.getInstance();
            Calendar dob = Calendar.getInstance();
            dob.setTime(date1);
            if (dob.after(now)) {
                throw new IllegalArgumentException("Can't be born in the future");
            }
            int year1 = now.get(Calendar.YEAR);
            int year2 = dob.get(Calendar.YEAR);
            age = year1 - year2;
            int month1 = now.get(Calendar.MONTH);
            int month2 = dob.get(Calendar.MONTH);
            if (month2 > month1) {
                age--;
            } else if (month1 == month2) {
                int day1 = now.get(Calendar.DAY_OF_MONTH);
                int day2 = dob.get(Calendar.DAY_OF_MONTH);
                if (day2 > day1) {
                    age--;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age;
    }

}
