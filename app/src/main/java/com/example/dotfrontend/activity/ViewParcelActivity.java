package com.example.dotfrontend.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.ParcelLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewParcelActivity extends AppCompatActivity {

    private EditText editParcelId;
    private Button buttonView;
    private TextView textParcelInfo;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parcel);

        editParcelId    = findViewById(R.id.edit_parcel_id);
        buttonView      = findViewById(R.id.button_view);
        textParcelInfo  = findViewById(R.id.text_parcel_info);

        apiService = ApiClient.getClient().create(ApiService.class);

        buttonView.setOnClickListener(v -> {
            String idStr = editParcelId.getText().toString().trim();
            if (idStr.isEmpty()) {
                Toast.makeText(this, "Please enter a Parcel ID", Toast.LENGTH_SHORT).show();
                return;
            }
            long parcelId;
            try {
                parcelId = Long.parseLong(idStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid Parcel ID", Toast.LENGTH_SHORT).show();
                return;
            }
            fetchParcelLog(parcelId);
        });
    }

    private void fetchParcelLog(long parcelId) {
        apiService.getParcelLog(parcelId)
                .enqueue(new Callback<ParcelLog>() {
                    @Override
                    public void onResponse(Call<ParcelLog> call, Response<ParcelLog> response) {
                        if (!response.isSuccessful()) {
                            textParcelInfo.setText("not succesful " + parcelId);
                            String err = "Error " + response.code();
                            Toast.makeText(ViewParcelActivity.this, err, Toast.LENGTH_LONG).show();
                            return;
                        }
                        if (response.body() == null) {
                            textParcelInfo.setText("No log found for ID " + parcelId);
                            return;
                        }
                        ParcelLog log = response.body();
                        StringBuilder sb = new StringBuilder()
                                .append("Log ID: ").append(log.getLogId()).append("\n")
                                .append("Status: ").append(log.getStatus()).append("\n")
                                .append("Placement Date: ").append(log.getPlacementDate()).append("\n")
                                .append("Location: ")
                                .append(log.getLocation() != null
                                        ? log.getLocation().getCity()
                                        : "N/A").append("\n")
                                .append("Current Rider ID: ")
                                .append("Delivered Date: ").append(log.getDeliveredDate());

                        textParcelInfo.setText(sb.toString());
                    }

                    @Override
                    public void onFailure(Call<ParcelLog> call, Throwable t) {
                        textParcelInfo.setText("Error: " + t.getMessage());
                    }
                });
    }
}
