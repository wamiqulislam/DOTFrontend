package com.example.dotfrontend.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.response.ChangeBatchProperties;
import com.example.dotfrontend.response.ChangeParcelPropertiesResponse;
import com.example.dotfrontend.util.SessionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;

public class ChangeParcelActivity extends AppCompatActivity {
    EditText etBatchId;
    Spinner spinnerLocation;
    Button btnDropBatch;
    ApiService api;
    SessionManager session;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_change_parcel);
        etBatchId = findViewById(R.id.etBatchId);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        btnDropBatch = findViewById(R.id.btnDropBatch);

        ArrayAdapter<CharSequence> locAdapter = ArrayAdapter.createFromResource(
                this, R.array.location_array,
                android.R.layout.simple_spinner_item
        );
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(locAdapter);

        api = ApiClient.getClient().create(ApiService.class);

        btnDropBatch.setOnClickListener(v -> dropBatch());
    }

    void dropBatch() {
        long bid = Long.parseLong(etBatchId.getText().toString());
        //long rid = session.getUserId();
        ChangeBatchProperties req = new ChangeBatchProperties();

        String locationFull = spinnerLocation.getSelectedItem().toString();
        String[] locationParts = locationFull.split(",\\s*");
        String city = locationParts[0];
        String country = locationParts[1];

        req.setBatchId(bid);
        //req.setRiderId(rid);

        Call<ResponseBody> c1 = api.dropBatch(bid);
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


        /*Call<ResponseBody> c2 = api.changeLocation(req);
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
        });*/

    }
}
