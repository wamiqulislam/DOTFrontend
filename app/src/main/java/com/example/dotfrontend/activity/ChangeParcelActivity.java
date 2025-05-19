package com.example.dotfrontend.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.Location;
import com.example.dotfrontend.response.ChangeBatchProperties;
import com.example.dotfrontend.response.ChangeParcelPropertiesResponse;
import com.example.dotfrontend.util.SessionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;

public class ChangeParcelActivity extends AppCompatActivity {
    Spinner spinnerLocation;
    Button btnDropBatch, btnChangeLocation, btnViewParcels;
    ApiService api;
    long batchId;
    private SessionManager session;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_change_parcel);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        batchId = getIntent().getLongExtra("batchId", -1L);
        btnDropBatch = findViewById(R.id.btnDropBatch);
        btnChangeLocation = findViewById(R.id.btnChangeLocation);
        btnViewParcels = findViewById(R.id.btnViewParcels);
        session = new SessionManager(getApplicationContext());

        ArrayAdapter<CharSequence> locAdapter = ArrayAdapter.createFromResource(
                this, R.array.location_array,
                android.R.layout.simple_spinner_item
        );
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(locAdapter);

        api = ApiClient.getClient().create(ApiService.class);

        btnDropBatch.setOnClickListener(v -> dropBatch());
        btnChangeLocation.setOnClickListener(v -> changeLocation());
        btnViewParcels.setOnClickListener(v -> {
            Intent i = new Intent(this, ViewParcelsActivity.class);
            i.putExtra("batchId", batchId);
            startActivity(i);
        });
    }

    void dropBatch() {

        Call<ResponseBody> c1 = api.dropBatch(batchId);
        c1.enqueue(new Callback<>() {
            @Override public void onResponse(Call<ResponseBody> cc, Response<ResponseBody> r) {
                Log.d("ChangeParcelActivity", "drop batch succeeded");
                Toast.makeText(ChangeParcelActivity.this,
                        r.isSuccessful()? "Done":"Err "+r.code(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<ResponseBody> cc, Throwable t) {
                Log.d("ChangeParcelActivity", "drop batch failed");
                Toast.makeText(ChangeParcelActivity.this,
                        "Net err", Toast.LENGTH_SHORT).show();
            }
        });

    }
    void changeLocation(){
        long rid = session.getUserId();
        ChangeBatchProperties req = new ChangeBatchProperties();

        String locationFull = spinnerLocation.getSelectedItem().toString();
        String[] locationParts = locationFull.split(",\\s*");
        String city = locationParts[0];
        String country = locationParts[1];

        req.setBatchId(batchId);
        req.setRiderId(rid);
        req.setLocation(city);

        Call<ResponseBody> c2 = api.changeLocation(req);
        c2.enqueue(new Callback<>() {
            @Override public void onResponse(Call<ResponseBody> cc, Response<ResponseBody> r) {
                Toast.makeText(ChangeParcelActivity.this,
                        r.isSuccessful()? "Done":"Err "+r.code(),
                        Toast.LENGTH_SHORT).show();
            }
            @Override public void onFailure(Call<ResponseBody> cc, Throwable t) {
                Toast.makeText(ChangeParcelActivity.this,
                        "Net err", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
