package com.mrtecks.yocreditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mrtecks.yocreditapp.statusPOJO.Datum;
import com.mrtecks.yocreditapp.statusPOJO.statusBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Loans extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView grid;
    ProgressBar progress;
    List<Datum> list;
    GridLayoutManager manager;
    LoanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loans);

        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar2);
        grid = findViewById(R.id.grid);
        progress = findViewById(R.id.progressBar2);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(R.drawable.arrowleft);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setTitle("My Loans");


        adapter = new LoanAdapter(this , list);
        manager = new GridLayoutManager(this , 1);

        grid.setAdapter(adapter);
        grid.setLayoutManager(manager);


    }

    @Override
    protected void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(b.baseurl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AllApiIneterface cr = retrofit.create(AllApiIneterface.class);

        Call<statusBean> call = cr.getLoans(
                SharePreferenceUtils.getInstance().getString("id")
        );

        call.enqueue(new Callback<statusBean>() {
            @Override
            public void onResponse(Call<statusBean> call, Response<statusBean> response) {

                adapter.setData(response.body().getData());

                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<statusBean> call, Throwable t) {
                progress.setVisibility(View.GONE);
            }
        });


    }

    class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.ViewHolder>
    {
        Context context;
        List<Datum> list = new ArrayList<>();

        public LoanAdapter(Context context , List<Datum> list)
        {
            this.context = context;
            this.list = list;
        }

        void setData(List<Datum> list)
        {
            this.list = list;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.loan_list_model , parent , false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            final Datum item = list.get(position);

            holder.title.setText("LOAN APPLICATION #" + item.getId());
            holder.date.setText("APPLICATION DATE - " + item.getCreated());
            holder.amount.setText(item.getAmount());
            holder.interest.setText(item.getInterest());
            holder.tenure.setText(item.getTenover());
            holder.status.setText(item.getStatus());

            if (item.getStatus().equals("pending"))
            {
                holder.status.setTextColor(getResources().getColor(R.color.colorAccent));
            }
            else if (item.getStatus().equals("approved"))
            {
                holder.status.setTextColor(getResources().getColor(R.color.color_green));
            }
            else if (item.getStatus().equals("rejected"))
            {
                holder.status.setTextColor(getResources().getColor(R.color.red));
            }
            else
            {
                holder.status.setTextColor(getResources().getColor(R.color.color_green));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context , LoanDetails.class);
                    intent.putExtra("id" , item.getId());
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder
        {

            TextView title , date , status;
            EditText amount , interest , tenure;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                title = itemView.findViewById(R.id.title);
                date = itemView.findViewById(R.id.date);
                amount = itemView.findViewById(R.id.amount);
                interest = itemView.findViewById(R.id.interest);
                tenure = itemView.findViewById(R.id.tenure);
                status = itemView.findViewById(R.id.status);

            }
        }
    }

}
