package com.mrtecks.yocredit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mrtecks.yocredit.loanDetailsPOJO.Data;
import com.mrtecks.yocredit.loanDetailsPOJO.Emi;
import com.mrtecks.yocredit.loanDetailsPOJO.loanDetailsBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoanDetails extends AppCompatActivity {

    Toolbar toolbar;
    TextView title , date , status;
    EditText amount , interest , tenure , pamount , paid;
    RecyclerView emi;
    Button pay;
    ProgressBar progress;
    String id;
    List<Emi> list;
    EMIAdapter adapter;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);


        id = getIntent().getStringExtra("id");
        list = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        amount = findViewById(R.id.amount);
        interest = findViewById(R.id.interest);
        tenure = findViewById(R.id.tenure);
        pamount = findViewById(R.id.pamount);
        paid = findViewById(R.id.paid);
        emi = findViewById(R.id.emis);
        pay = findViewById(R.id.submit);
        progress = findViewById(R.id.progress);
        status = findViewById(R.id.status);

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

        toolbar.setTitle("LOAN APPLICATION #" + id);

        adapter = new EMIAdapter(this , list);
        manager = new GridLayoutManager(this , 1);

        emi.setAdapter(adapter);
        emi.setLayoutManager(manager);



    }

    @Override
    public void onResume() {
        super.onResume();


        progress.setVisibility(View.VISIBLE);

        Bean b = (Bean) getApplicationContext();

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

                    float iam = (inn / 100) * amm;

                    float nam = amm + iam;

                    pamount.setText(String.valueOf(nam));
                    paid.setText(item.getPaid());

                    adapter.setData(item.getEmi());
                    status.setText(item.getStatus());

                    if (item.getStatus().equals("pending"))
                    {
                        status.setTextColor(getResources().getColor(R.color.colorAccent));
                    }
                    else if (item.getStatus().equals("approved"))
                    {
                        status.setTextColor(getResources().getColor(R.color.color_green));
                    }
                    else if (item.getStatus().equals("rejected"))
                    {
                        status.setTextColor(getResources().getColor(R.color.red));
                    }
                    else
                    {
                        status.setTextColor(getResources().getColor(R.color.color_green));
                    }


                }
                else
                {
                    Toast.makeText(LoanDetails.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
