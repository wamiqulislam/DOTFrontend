package com.example.dotfrontend.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.ChangeDueAmountResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminInterfaceActivity extends AppCompatActivity {

    private EditText etRiderId, etAmount;
    private Button btnSetAmount;
    private ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        etRiderId   = findViewById(R.id.etRiderId);
        etAmount    = findViewById(R.id.etAmount);
        btnSetAmount= findViewById(R.id.btnSetAmount);

        // Retrofit setup (adjust baseUrl to your backend)
        apiService = ApiClient.getClient().create(ApiService.class);

        btnSetAmount.setOnClickListener(v -> onChangeDueClicked());
    }

    private void onChangeDueClicked() {
        String sId    = etRiderId.getText().toString().trim();
        String sAmount= etAmount.getText().toString().trim();

        if (TextUtils.isEmpty(sId) || TextUtils.isEmpty(sAmount)) {
            Toast.makeText(this, "Please enter both Rider ID and Amount", Toast.LENGTH_SHORT).show();
            return;
        }

        long riderId;
        float amount;
        try {
            riderId = Long.parseLong(sId);
            amount  = Float.parseFloat(sAmount);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
            return;
        }

        ChangeDueAmountResponse body = new ChangeDueAmountResponse();
        body.setRiderId(riderId);
        body.setAmount(amount);

        apiService.changeDueAmount(body).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AdminInterfaceActivity.this,
                            "Due amount updated", Toast.LENGTH_SHORT).show();
                    etRiderId.setText("");
                    etAmount.setText("");
                } else {
                    Toast.makeText(AdminInterfaceActivity.this,
                            "Failed: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminInterfaceActivity.this,
                        "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
