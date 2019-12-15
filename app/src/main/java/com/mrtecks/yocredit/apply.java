package com.mrtecks.yocredit;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class apply extends Fragment {

    EditText amount , interest;
    Spinner tenure;
    Button submit;

    double inter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.apply , container , false);

        amount = view.findViewById(R.id.amount);
        interest = view.findViewById(R.id.interest);
        tenure = view.findViewById(R.id.tenure);
        submit = view.findViewById(R.id.submit);

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

        return view;
    }
}
