package com.example.dotfrontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dotfrontend.R;
import com.example.dotfrontend.adapter.BatchViewAdapter;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.Batch;
import com.example.dotfrontend.util.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBatchesActivity extends AppCompatActivity {

    private RecyclerView rvBatches;
    private SessionManager session;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_batches);

        rvBatches = findViewById(R.id.rvBatches);
        rvBatches.setLayoutManager(new LinearLayoutManager(this));

        session = new SessionManager(this);
        api     = ApiClient.getClient().create(ApiService.class);

        fetchAndShowBatches();
        Log.d("BatchViewAdapter", "........................................20");
    }

    private void fetchAndShowBatches() {
        long riderId = session.getUserId();
        api.getRiderBatches(riderId)
                .enqueue(new Callback<List<Batch>>() {
                    @Override
                    public void onResponse(Call<List<Batch>> call,
                                           Response<List<Batch>> resp) {
                        if (!resp.isSuccessful()) {
                            Log.d("BatchViewAdapter", "........................................40");

                            Toast.makeText(ViewBatchesActivity.this,
                                    "Error: " + resp.code(),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Log.d("BatchViewAdapter", "........................................42");

                        List<Batch> list = resp.body();
                        BatchViewAdapter adapter = new BatchViewAdapter(
                                list,
                                batch -> {
                                    Intent i = new Intent(
                                            ViewBatchesActivity.this,
                                            ChangeParcelActivity.class
                                    );
                                    i.putExtra("batchId", batch.getBatchId());
                                    startActivity(i);
                                }
                        );
                        rvBatches.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<Batch>> call, Throwable t) {

                        Log.d("BatchViewAdapter", "........................................41");
                        Log.e("BatchViewAdapter", "onFailure: ", t);


                        Toast.makeText(ViewBatchesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
