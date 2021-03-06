package com.mrtecks.yocreditapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mrtecks.yocreditapp.loanDetailsPOJO.Data;
import com.mrtecks.yocreditapp.loanDetailsPOJO.loanDetailsBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class accepted extends Fragment {
    TextView title , date;
    EditText amount , interest , tenure;
    String id;
    ProgressBar progress;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.accepted , container , false);

        id = getArguments().getString("id");

        title = view.findViewById(R.id.title);
        date = view.findViewById(R.id.date);
        amount = view.findViewById(R.id.amount);
        interest = view.findViewById(R.id.interest);
        tenure = view.findViewById(R.id.tenure);
        progress = view.findViewById(R.id.progress);


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
