package com.example.dotfrontend.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.response.SendParcelResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;

public class SendParcelActivity extends AppCompatActivity {
    EditText etCustId, etWeight, etPlacementDate, etSenderAddress, etAddress;
    Spinner spinnerType, spinnerOrigin, spinnerDestination;
    Button btnSend;
    ApiService api;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_send_parcel);

        etCustId         = findViewById(R.id.etCustomerId);
        spinnerType      = findViewById(R.id.spinnerParcelType);
        etWeight         = findViewById(R.id.etWeight);
        spinnerOrigin      = findViewById(R.id.spinnerOrigin);
        etSenderAddress  = findViewById(R.id.etSenderAddress);
        spinnerDestination = findViewById(R.id.spinnerDestination);
        etAddress        = findViewById(R.id.etAddress);
        etPlacementDate  = findViewById(R.id.etPlacementDate);
        btnSend          = findViewById(R.id.btnSendParcel);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.parcel_type_array,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> locAdapter = ArrayAdapter.createFromResource(
                this, R.array.location_array,
                android.R.layout.simple_spinner_item
        );
        locAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOrigin.setAdapter(locAdapter);
        spinnerDestination.setAdapter(locAdapter);

        etPlacementDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        String mm = (m+1<10?"0":"") + (m+1);
                        String dd = (d<10?"0":"") + d;
                        etPlacementDate.setText(y + "-" + mm + "-" + dd);
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        api = ApiClient.getClient().create(ApiService.class);
        btnSend.setOnClickListener(v -> {

            String originFull      = spinnerOrigin.getSelectedItem().toString();
            String destinationFull = spinnerDestination.getSelectedItem().toString();

            String[] originParts      = originFull.split(",\\s*");
            String[] destinationParts = destinationFull.split(",\\s*");

            long   custId = Long.parseLong(etCustId.getText().toString());
            String type   = spinnerType.getSelectedItem().toString();
            float weight = (float) Double.parseDouble(etWeight.getText().toString());
            String oci = originParts[0];
            String oc  = originParts[1];
            String dci = destinationParts[0];
            String dc  = destinationParts[1];
            String pd     = etPlacementDate.getText().toString();
            String sender = etSenderAddress.getText().toString();
            String address = etAddress.getText().toString();

            if (weight > 100) {
                Toast.makeText(this, "Parcel weight cannot exceed 100Kg", Toast.LENGTH_SHORT).show();
                return;
            }

            // **SWAP** origin/destination fields to match backend's reversed findByCityAndCountry(...)
            SendParcelResponse req = new SendParcelResponse(
                    custId,
                    dci,
                    dc,
                    address,
                    oci,
                    oc,
                    sender,
                    pd,
                    type,
                    weight
            );

            api.addParcel(req).enqueue(new Callback<ResponseBody>() {
                @Override public void onResponse(Call<ResponseBody> c, Response<ResponseBody> r) {
                    Toast.makeText(
                            SendParcelActivity.this,
                            r.isSuccessful() ? "Parcel sent" : "Error " + r.code(),
                            Toast.LENGTH_LONG
                    ).show();
                }
                @Override public void onFailure(Call<ResponseBody> c, Throwable t) {
                    Toast.makeText(
                            SendParcelActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        });
    }
}
