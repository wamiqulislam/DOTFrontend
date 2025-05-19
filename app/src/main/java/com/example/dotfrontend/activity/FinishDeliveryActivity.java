package com.example.dotfrontend.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.extras.ParcelStatus;
import com.example.dotfrontend.response.ChangeParcelPropertiesResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishDeliveryActivity extends AppCompatActivity {
    private EditText etParcelId, etDate;
    private Spinner spinnerStatus;
    private Button btnFinish;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_delivery);

        etParcelId    = findViewById(R.id.etParcelId);
        etDate        = findViewById(R.id.etDate);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        btnFinish     = findViewById(R.id.btnFinish);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.parcel_status_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);

        etDate.setOnClickListener(v -> showDatePicker());

        // ← use your existing ApiClient
        api = ApiClient.getClient().create(ApiService.class);

        btnFinish.setOnClickListener(v -> submit());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(
                this,
                (view, y, m, d) -> {
                    m++;
                    String mm = (m<10?"0"+m:m)+"";
                    String dd = (d<10?"0"+d:d)+"";
                    etDate.setText(y + "-" + mm + "-" + dd);
                },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void submit() {
        Long parcelId = Long.parseLong(etParcelId.getText().toString());
        ParcelStatus status = ParcelStatus.valueOf(
                spinnerStatus.getSelectedItem().toString()
        );
        String deliveredDate = etDate.getText().toString();

        ChangeParcelPropertiesResponse body =
                new ChangeParcelPropertiesResponse();

        body.setParcelId(parcelId);
        body.setStatus(status);
        body.setDeliveredDate(deliveredDate);

        api.changeDeliveredDate(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> r) {
                if (r.isSuccessful()) {
                    Toast.makeText(FinishDeliveryActivity.this,
                            "Delivery updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FinishDeliveryActivity.this,
                            "Error: " + r.code(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(FinishDeliveryActivity.this,
                        t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
