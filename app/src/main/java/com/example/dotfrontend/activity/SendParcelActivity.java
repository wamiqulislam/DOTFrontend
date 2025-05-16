package com.example.dotfrontend.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.SendParcelResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Calendar;

public class SendParcelActivity extends AppCompatActivity {
    EditText etCustId, etWeight, etOriginCity, etOriginCountry,
            etDestCity, etDestCountry, etPlacementDate;
    Spinner spinnerType;
    Button btnSend;
    ApiService api;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_send_parcel);

        etCustId         = findViewById(R.id.etCustomerId);
        spinnerType      = findViewById(R.id.spinnerParcelType);
        etWeight         = findViewById(R.id.etWeight);
        etOriginCity     = findViewById(R.id.etOriginCity);
        etOriginCountry  = findViewById(R.id.etOriginCountry);
        etDestCity       = findViewById(R.id.etDestinationCity);
        etDestCountry    = findViewById(R.id.etDestinationCountry);
        etPlacementDate  = findViewById(R.id.etPlacementDate);
        btnSend          = findViewById(R.id.btnSendParcel);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.parcel_type_array,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

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
            long   custId = Long.parseLong(etCustId.getText().toString());
            String type   = spinnerType.getSelectedItem().toString();
            double weight = Double.parseDouble(etWeight.getText().toString());
            String oc     = etOriginCountry.getText().toString();
            String oci    = etOriginCity.getText().toString();
            String dc     = etDestCountry.getText().toString();
            String dci    = etDestCity.getText().toString();
            String pd     = etPlacementDate.getText().toString();

            // **SWAP** origin/destination fields to match backend's reversed findByCityAndCountry(...)
            SendParcelResponse req = new SendParcelResponse(
                    custId,
                    type,
                    weight,
                    /* originCountry  = city */      oci,
                    /* originCity     = country */   oc,
                    /* destinationCountry = city */  dci,
                    /* destinationCity    = country */ dc,
                    pd
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
