package com.mrtecks.yocredit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mrtecks.yocredit.loanDetailsPOJO.Data;
import com.mrtecks.yocredit.loanDetailsPOJO.loanDetailsBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class rejected extends Fragment {
    TextView title , date;
    EditText amount , interest , tenure;
    String id;
    ProgressBar progress;
    Button apply;

    String appDate = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rejected , container , false);

        id = getArguments().getString("id");

        title = view.findViewById(R.id.title);
        date = view.findViewById(R.id.date);
        amount = view.findViewById(R.id.amount);
        interest = view.findViewById(R.id.interest);
        tenure = view.findViewById(R.id.tenure);
        progress = view.findViewById(R.id.progress);
        apply = view.findViewById(R.id.submit);

        apply.setVisibility(View.GONE);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String dtStart = appDate;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date stDate = null;
                try {
                    stDate = format.parse(dtStart);
                    System.out.println(stDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long now = System.currentTimeMillis() - 1000;

                Date endDate = new Date();


                long ddd = (endDate.getTime() - stDate.getTime()) / (24 * 60 * 60 * 1000);

                ddd++;

                Log.d("diff" , String.valueOf(ddd));

                if (ddd > 30)
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    apply test = new apply();
                    ft.replace(R.id.replace, test);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                else
                {
                    Toast.makeText(getContext(), "You are not eligible for this loan, please try after 30 days of application", Toast.LENGTH_SHORT).show();
                }



            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getActivity().getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<loanDetailsBean> call = cr.getLoanDetails(
                SharePreferenceUtils.getInstance().getString("id"),
                id
        );

        call.enqueue(new Callback<loanDetailsBean>() {
            @Override
            public void onResponse(Call<loanDetailsBean> call, Response<loanDetailsBean> response) {

                if (response.body().getStatus().equals("1"))
                {

                    Data item = response.body().getData();

                    title.setText("LOAN APPLICATION #" + item.getId());
                    date.setText("APPLICATION DATE - " + item.getCreated());
                    amount.setText(item.getAmount());
                    interest.setText(item.getInterest());
                    tenure.setText(item.getTenover());
                    //pamount.setText(item.g);

                    appDate = item.getCreated();

                    apply.setVisibility(View.VISIBLE);

                }
                else
                {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }



                progress.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<loanDetailsBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

}
