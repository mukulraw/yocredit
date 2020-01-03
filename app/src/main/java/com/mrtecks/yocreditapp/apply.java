package com.mrtecks.yocreditapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.mrtecks.yocreditapp.statusPOJO.statusBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class apply extends Fragment {

    EditText amount , interest;
    Spinner tenure;
    Button submit;
    String t = "";

    ProgressBar progress;

    double inter = 0;
    List<String> ten;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.apply , container , false);

        ten = new ArrayList<>();

        amount = view.findViewById(R.id.amount);
        interest = view.findViewById(R.id.interest);
        tenure = view.findViewById(R.id.tenure);
        submit = view.findViewById(R.id.submit);
        progress = view.findViewById(R.id.progress);

        amount.setFilters(new InputFilter[]{new InputFilterMinMax("1", "500000")});

        amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 0)
                {
                    float val = Float.parseFloat(s.toString());

                    if (val < 1000)
                    {
                        amount.setError("Invalid Amount");
                    }

                    if (val >= 1000 && val < 10000)
                    {
                        inter = 1.95;
                        interest.setText(inter + "%");
                    }
                    else if (val >= 10000 && val < 50000)
                    {
                        inter = 1.85;
                        interest.setText(inter + "%");
                    }
                    else if (val >= 50000 && val < 100000)
                    {
                        inter = 1.75;
                        interest.setText(inter + "%");
                    }
                    else if (val >= 100000 && val < 200000)
                    {
                        inter = 1.65;
                        interest.setText(inter + "%");
                    }
                    else if (val >= 200000 && val <= 500000)
                    {
                        inter = 1.55;
                        interest.setText(inter + "%");
                    }
                    else
                    {
                        inter = 0;
                        interest.setText(inter + "%");
                    }
                }
                else
                {
                    inter = 0;
                    interest.setText(inter + "%");
                    amount.setError("Invalid Amount");
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ten.add("1 month");
        ten.add("2 months");
        ten.add("3 months");
        ten.add("4 months");
        ten.add("5 months");
        ten.add("6 months");
        ten.add("7 months");
        ten.add("8 months");
        ten.add("9 months");
        ten.add("10 months");
        ten.add("11 months");
        ten.add("12 months");
        ten.add("24 months");
        ten.add("36 months");
        ten.add("48 months");
        ten.add("60 months");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1,ten);

        tenure.setAdapter(adapter);

        tenure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                t = ten.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = amount.getText().toString();

                if (a.length() > 0)
                {
                    float aa = Float.parseFloat(a);
                    if (aa > 999)
                    {

                        submit.setEnabled(false);
                        submit.setClickable(false);


                        progress.setVisibility(View.VISIBLE);

                        Bean b = (Bean) getActivity().getApplicationContext();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseurl)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

                        Call<statusBean> call = cr.applyLoan(
                                SharePreferenceUtils.getInstance().getString("id"),
                                a,
                                String.valueOf(inter),
                                t
                        );

                        call.enqueue(new Callback<statusBean>() {
                            @Override
                            public void onResponse(Call<statusBean> call, Response<statusBean> response) {


                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                progress.setVisibility(View.GONE);

                                ((MainActivity)getActivity()).getStatus();


                            }

                            @Override
                            public void onFailure(Call<statusBean> call, Throwable t) {
                                progress.setVisibility(View.GONE);

                                submit.setEnabled(true);
                                submit.setClickable(true);

                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(getContext(), "Invalid amount", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Invalid amount", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
