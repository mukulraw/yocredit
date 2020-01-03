package com.mrtecks.yocreditapp;

import android.content.Context;
import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mrtecks.yocreditapp.loanDetailsPOJO.Data;
import com.mrtecks.yocreditapp.loanDetailsPOJO.Emi;
import com.mrtecks.yocreditapp.loanDetailsPOJO.loanDetailsBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class approved extends Fragment {

    TextView title , date;
    EditText amount , interest , tenure , pamount , paid;
    RecyclerView emi;
    Button pay;
    ProgressBar progress;
    String id;
    List<Emi> list;
    EMIAdapter adapter;
    GridLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.approved , container , false);

        id = getArguments().getString("id");
        list = new ArrayList<>();

        title = view.findViewById(R.id.title);
        date = view.findViewById(R.id.date);
        amount = view.findViewById(R.id.amount);
        interest = view.findViewById(R.id.interest);
        tenure = view.findViewById(R.id.tenure);
        pamount = view.findViewById(R.id.pamount);
        paid = view.findViewById(R.id.paid);
        emi = view.findViewById(R.id.emis);
        pay = view.findViewById(R.id.submit);
        progress = view.findViewById(R.id.progress);

        adapter = new EMIAdapter(getActivity() , list);
        manager = new GridLayoutManager(getActivity() , 1);

        emi.setAdapter(adapter);
        emi.setLayoutManager(manager);


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext() , PayEMI.class);
                intent.putExtra("id" , id);
                startActivity(intent);

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

                    float amm = Float.parseFloat(item.getAmount());
                    float inn = Float.parseFloat(item.getInterest());

                    String[] spl = item.getTenover().split(" ");

                    float inn1 = Float.parseFloat(spl[0]);

                    inn = inn * inn1;

                    Log.d("inn" , String.valueOf(inn));

                    float iam = (inn / 100) * amm;

                    float nam = amm + iam;

                    pamount.setText(String.valueOf(nam));
                    paid.setText(item.getPaid());

                    adapter.setData(item.getEmi());

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

    class EMIAdapter extends RecyclerView.Adapter<EMIAdapter.ViewHolder>
    {

        List<Emi> list = new ArrayList<>();
        Context context;

        public EMIAdapter(Context context , List<Emi> list)
        {
            this.context = context;
            this.list = list;
        }

        void setData(List<Emi> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.emi_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Emi item = list.get(position);


            holder.amount.setText(item.getAmount());
            holder.date.setText(item.getCreated());
            holder.status.setText(item.getStatus());

            if (item.getStatus().equals("pending"))
            {
                holder.status.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            else if (item.getStatus().equals("approved"))
            {
                holder.status.setTextColor(getResources().getColor(R.color.color_green));
            }
            else
            {
                holder.status.setTextColor(getResources().getColor(R.color.red));
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            EditText amount , date;
            TextView status;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                amount = itemView.findViewById(R.id.editText2);
                date = itemView.findViewById(R.id.editText3);
                status = itemView.findViewById(R.id.textView14);

            }
        }
    }

}
