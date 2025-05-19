package com.example.dotfrontend.activity;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dotfrontend.R;
import com.example.dotfrontend.adapter.ParcelAdapter;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.Parcel;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewParcelsActivity extends AppCompatActivity {
    private RecyclerView rvParcels;
    private ApiService api;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_view_parcels);

        rvParcels = findViewById(R.id.rvParcels);
        rvParcels.setLayoutManager(new LinearLayoutManager(this));

        api = ApiClient.getClient().create(ApiService.class);

        long batchId = getIntent().getLongExtra("batchId", -1L);
        fetchParcels(batchId);
    }

    private void fetchParcels(long batchId) {
        api.getParcelsOfBatch(batchId).enqueue(new Callback<List<Parcel>>() {
            @Override
            public void onResponse(Call<List<Parcel>> c, Response<List<Parcel>> r) {
                if (!r.isSuccessful()) {
                    Toast.makeText(ViewParcelsActivity.this,
                            "Error: " + r.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Parcel> list = r.body();
                ParcelAdapter adapter = new ParcelAdapter(list);
                rvParcels.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<Parcel>> c, Throwable t) {
                Toast.makeText(ViewParcelsActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

