package com.example.dotfrontend.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotfrontend.R;
import com.example.dotfrontend.adapter.ParcelCustomerAdapter;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.Parcel;
import com.example.dotfrontend.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewParcelActivity extends AppCompatActivity {
    private RecyclerView rvParcels;
    private SessionManager session;
    private ApiService api;
    private ParcelCustomerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parcel);

        rvParcels = findViewById(R.id.rvParcels);
        rvParcels.setLayoutManager(new LinearLayoutManager(this));

        session = new SessionManager(this);
        api     = ApiClient.getClient().create(ApiService.class);

        adapter = new ParcelCustomerAdapter(new ArrayList<>());
        rvParcels.setAdapter(adapter);

        fetchParcelsForCustomer();
    }

    private void fetchParcelsForCustomer() {
        long customerId = session.getUserId();
        api.getParcelsOfCustomer(customerId)
                .enqueue(new Callback<List<Parcel>>() {
                    @Override
                    public void onResponse(Call<List<Parcel>> call,
                                           Response<List<Parcel>> resp) {
                        if (!resp.isSuccessful()) {
                            Toast.makeText(ViewParcelActivity.this,
                                    "Error: " + resp.code(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<Parcel> list = resp.body();
                        adapter.updateData(list != null
                                ? list
                                : new ArrayList<>());
                    }
                    @Override
                    public void onFailure(Call<List<Parcel>> call, Throwable t) {
                        Toast.makeText(ViewParcelActivity.this,
                                "Failure: " + t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
