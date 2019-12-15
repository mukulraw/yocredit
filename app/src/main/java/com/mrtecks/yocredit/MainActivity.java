package com.mrtecks.yocredit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mrtecks.yocredit.statusPOJO.statusBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    ImageView menu, notification;
    DrawerLayout drawer;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menu = findViewById(R.id.imageButton2);
        notification = findViewById(R.id.imageButton);
        drawer = findViewById(R.id.drawer);
        progress = findViewById(R.id.progressBar);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }

            }
        });

        getStatus();

    }

    void getStatus() {

        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<statusBean> call = cr.getStatus(
                SharePreferenceUtils.getInstance().getString("id")
        );

        call.enqueue(new Callback<statusBean>() {
            @Override
            public void onResponse(Call<statusBean> call, Response<statusBean> response) {

                if (response.body().getStatus().equals("1")) {

                    if (response.body().getData().size() > 0) {
                        // loan applied

                        String status = response.body().getData().get(0).getStatus();
                        if (status.equals("pending")) {
                            // loan request pending
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            pending test = new pending();
                            Bundle b = new Bundle();
                            b.putString("id" , response.body().getData().get(0).getId());
                            test.setArguments(b);
                            ft.replace(R.id.replace, test);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                            //ft.addToBackStack(null);
                            ft.commit();
                        } else if (status.equals("rejected")) {
                            // loan request rejected
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            rejected test = new rejected();
                            Bundle b = new Bundle();
                            b.putString("id" , response.body().getData().get(0).getId());
                            test.setArguments(b);
                            ft.replace(R.id.replace, test);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                            //ft.addToBackStack(null);
                            ft.commit();
                        } else {
                            // loan request approved
                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            approved test = new approved();
                            Bundle b = new Bundle();
                            b.putString("id" , response.body().getData().get(0).getId());
                            test.setArguments(b);
                            ft.replace(R.id.replace, test);
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                            //ft.addToBackStack(null);
                            ft.commit();
                        }

                    } else {
                        // no loan applied
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        apply test = new apply();
                        ft.replace(R.id.replace, test);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        //ft.addToBackStack(null);
                        ft.commit();

                    }

                } else {
                    // profile rejected
                }

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<statusBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });

    }

}
