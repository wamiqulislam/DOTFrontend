// ChooseParcelActivity.java
package com.example.dotfrontend.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotfrontend.R;
import com.example.dotfrontend.adapter.BatchAdapter;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.Batch;
import com.example.dotfrontend.request.AssignRiderRequest;
import com.example.dotfrontend.util.SessionManager;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseParcelActivity extends AppCompatActivity {
    EditText etCity, etCountry;
    Button btnSearch, btnAssign;
    RecyclerView rvBatches;
    BatchAdapter adapter;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_parcel);
        session = new SessionManager(this);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        btnSearch = findViewById(R.id.btnSearch);
        btnAssign = findViewById(R.id.btnAssign);
        rvBatches = findViewById(R.id.rvBatches);
        rvBatches.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BatchAdapter(new ArrayList<>());
        rvBatches.setAdapter(adapter);

        btnSearch.setOnClickListener(v -> fetchBatches());
        btnAssign.setOnClickListener(v -> assignSelected());
    }

    void fetchBatches() {
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if (city.isEmpty() || country.isEmpty()) {
            Toast.makeText(this, "Enter both city and country", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiService api = ApiClient.getClient().create(ApiService.class);
        Call<List<Batch>> call = api.getBatchesByLocation(city, country);
        call.enqueue(new Callback<List<Batch>>() {
            @Override
            public void onResponse(Call<List<Batch>> c, Response<List<Batch>> r) {
                if (r.isSuccessful() && r.body() != null) {
                    adapter.updateData(r.body());
                } else {
                    Toast.makeText(ChooseParcelActivity.this, "No batches found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Batch>> c, Throwable t) {
                Toast.makeText(ChooseParcelActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void assignSelected() {
        List<Batch> selected = adapter.getSelected();
        if (selected.isEmpty()) {
            Toast.makeText(this, "Select at least one batch", Toast.LENGTH_SHORT).show();
            return;
        }
        long riderId = session.getUserId();
        ApiService api = ApiClient.getClient().create(ApiService.class);
        // assign each selected batch to rider

        for (Batch b : selected) {
            Call<Batch> call = api.assignRiderToBatch(new AssignRiderRequest(b.getBatchId(), riderId));
            call.enqueue(new Callback<Batch>() {
                @Override
                public void onResponse(Call<Batch> c, Response<Batch> r) { }
                @Override
                public void onFailure(Call<Batch> c, Throwable t) { }
            });
        }
        Toast.makeText(this, "Assigned " + selected.size() + " batch(es)", Toast.LENGTH_SHORT).show();
    }
}
