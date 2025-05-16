package com.example.dotfrontend.activity;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.extras.VehicleStatus;
import com.example.dotfrontend.model.RegisterVehicleResponse;
import com.example.dotfrontend.extras.VehicleType;
import com.example.dotfrontend.util.SessionManager;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterVehicleActivity extends AppCompatActivity {
    EditText etRiderId, etModel, etLicense, etCapacity;
    Spinner spinnerType, spinnerStatus;
    Button btnReg;
    ApiService api;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_register_vehicle);
        etRiderId = findViewById(R.id.etRiderId);
        spinnerType = findViewById(R.id.spinnerVehicleType);
        etModel = findViewById(R.id.etModel);
        etLicense = findViewById(R.id.etLicense);
        spinnerStatus = findViewById(R.id.spinnerVehicleStatus);
        etCapacity = findViewById(R.id.etCapacity);
        btnReg = findViewById(R.id.btnRegisterVehicle);

        SessionManager session = new SessionManager(this);
        long userId = session.getUserId();

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(
                this, R.array.vehicle_type_array,
                android.R.layout.simple_spinner_item
        );
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                this, R.array.vehicle_status_array,
                android.R.layout.simple_spinner_item
        );
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        api = ApiClient.getClient().create(ApiService.class);
        btnReg.setOnClickListener(v -> {
            long riderId = Long.parseLong(etRiderId.getText().toString());
            String mdl = etModel.getText().toString();
            String lic = etLicense.getText().toString();
            String st = spinnerStatus.getSelectedItem().toString();
            String t  = spinnerType.getSelectedItem().toString();
            Double cap = Double.parseDouble(etCapacity.getText().toString());

            VehicleType vt = VehicleType.valueOf(t);
            VehicleStatus vst = VehicleStatus.valueOf(st);

            RegisterVehicleResponse req = new RegisterVehicleResponse(
                    riderId, vt, mdl, lic, vst, cap
            );
            api.registerVehicle(req).enqueue(new Callback<ResponseBody>() {
                @Override public void onResponse(Call<ResponseBody> c, Response<ResponseBody> r) {
                    Toast.makeText(RegisterVehicleActivity.this,
                            r.isSuccessful()? "Registered":"Err "+r.code(),
                            Toast.LENGTH_SHORT).show();
                }
                @Override public void onFailure(Call<ResponseBody> c, Throwable t) {
                    Toast.makeText(RegisterVehicleActivity.this,
                            "Net err", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
