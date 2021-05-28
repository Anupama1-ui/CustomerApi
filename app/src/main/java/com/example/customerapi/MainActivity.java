package com.example.customerapi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.customerapi.GetServicesData;
import com.example.customerapi.R;
import com.example.customerapi.RetrofitClientInstance;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private AppCompatButton GetData;
    private ProgressDialog mProgressDialog;
    private AppCompatTextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetData=findViewById(R.id.getData);
        mTextView=findViewById(R.id.textview);
        mProgressDialog=new ProgressDialog(this);

        GetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromServer();
            }
        });


    }

    private void getDataFromServer() {
        mProgressDialog.show();
        mProgressDialog.setMessage("Please Wait.....");
       GetServicesData getDataServices= RetrofitClientInstance.getRetrofitInstance().create(GetServicesData.class);
        Call<JsonObject> call=getDataServices.getCustomerData();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                mProgressDialog.dismiss();


                try {
                    JSONObject jsonObject=new JSONObject(new Gson().toJson(response.body()));

                    JSONObject jsonObject1=jsonObject.getJSONObject("data");


                    mTextView.setText("id : "+jsonObject1.getString("id"));


                    Toast.makeText(MainActivity.this, response.body()+"", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onResponse: "+response.body());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                mProgressDialog.dismiss();
                Toast.makeText(MainActivity.this, t.getMessage()+"", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onResponse: "+t.getMessage());
            }
        });

    }
}