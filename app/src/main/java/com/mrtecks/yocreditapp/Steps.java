package com.mrtecks.yocreditapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Steps extends AppCompatActivity {
    Button login;
    TextView basic , personal , document , verification , whatsapp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        login = findViewById(R.id.button2);
        basic = findViewById(R.id.textView7);
        personal = findViewById(R.id.textView8);
        document = findViewById(R.id.textView9);
        verification = findViewById(R.id.textView10);
        whatsapp = findViewById(R.id.textView16);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PackageManager pm=getPackageManager();
                try {


                    String toNumber = "917428122553"; // Replace with mobile phone number without +Sign or leading zeros, but with country code.
                    //Suppose your country is India and your phone number is “xxxxxxxxxx”, then you need to send “91xxxxxxxxxx”.



                    Intent sendIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + "" + toNumber + "?body=" + ""));
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(Steps.this,"it may be you dont have whats app",Toast.LENGTH_LONG).show();

        }

            }
        });

        if (SharePreferenceUtils.getInstance().getString("pin").length() > 0)
        {
            basic.setCompoundDrawablesWithIntrinsicBounds(null , null, getResources().getDrawable(R.drawable.ic_checked), null);
        }
        else
        {
            basic.setCompoundDrawablesWithIntrinsicBounds(null , null, null, null);
        }

        if (SharePreferenceUtils.getInstance().getString("dob").length() > 0)
        {
            personal.setCompoundDrawablesWithIntrinsicBounds(null , null, getResources().getDrawable(R.drawable.ic_checked), null);
        }
        else
        {
            personal.setCompoundDrawablesWithIntrinsicBounds(null , null, null, null);
        }

        if (SharePreferenceUtils.getInstance().getString("amount").length() > 0)
        {
            document.setCompoundDrawablesWithIntrinsicBounds(null , null, getResources().getDrawable(R.drawable.ic_checked), null);
        }
        else
        {
            document.setCompoundDrawablesWithIntrinsicBounds(null , null, null, null);
        }

        if (SharePreferenceUtils.getInstance().getString("status").equals("approved"))
        {
            verification.setCompoundDrawablesWithIntrinsicBounds(null , null, getResources().getDrawable(R.drawable.ic_checked), null);
        }
        else
        {
            verification.setCompoundDrawablesWithIntrinsicBounds(null , null, null, null);
        }



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SharePreferenceUtils.getInstance().getString("pan").length() > 0)
                {
                    Intent intent = new Intent(Steps.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (SharePreferenceUtils.getInstance().getString("dob").length() > 0)
                {
                    Intent intent = new Intent(Steps.this , Document.class);
                    startActivity(intent);
                    finish();
                }
                else if (SharePreferenceUtils.getInstance().getString("pin").length() > 0)
                {
                    Intent intent = new Intent(Steps.this , Personal.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(Steps.this , Basic.class);
                    startActivity(intent);
                    finish();
                }



            }
        });

    }
}
