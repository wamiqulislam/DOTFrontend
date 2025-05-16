package com.example.dotfrontend.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.model.ChangeParcelPropertiesResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.Calendar;

public class ChangeParcelActivity extends AppCompatActivity {
    EditText etParcelId, etLocation, etRiderId, etDeliveredDate;
    Spinner spinnerStatus;
    Button bs, bl, br, bd;
    ApiService api;

    @Override protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_change_parcel);
        etParcelId = findViewById(R.id.etParcelId);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        etLocation = findViewById(R.id.etLocation);
        etRiderId = findViewById(R.id.etRiderId);
        etDeliveredDate = findViewById(R.id.etDeliveredDate);
        bs = findViewById(R.id.btnChangeStatus);
        bl = findViewById(R.id.btnChangeLocation);
        br = findViewById(R.id.btnChangeRider);
        bd = findViewById(R.id.btnChangeDeliveredDate);

        ArrayAdapter<CharSequence> stAdapter = ArrayAdapter.createFromResource(
                this, R.array.parcel_status_array,
                android.R.layout.simple_spinner_item
        );
        stAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(stAdapter);

        etDeliveredDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this,
                    (view, y, m, d) -> {
                        String mm = (m+1<10?"0":"")+(m+1);
                        String dd = (d<10?"0":"")+d;
                        etDeliveredDate.setText(y+"-"+mm+"-"+dd);
                    },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
            ).show();
        });

        api = ApiClient.getClient().create(ApiService.class);

        bs.setOnClickListener(v -> callChange("changeStatus"));
        bl.setOnClickListener(v -> callChange("changeLocation"));
        br.setOnClickListener(v -> callChange("changeCurrentRider"));
        bd.setOnClickListener(v -> callChange("changeDeliveredDate"));
    }

    void callChange(String path) {
        long pid = Long.parseLong(etParcelId.getText().toString());
        ChangeParcelPropertiesResponse req = new ChangeParcelPropertiesResponse();
        req.setParcelId(pid);
        switch(path) {
            case "changeStatus":
                req.setStatus(spinnerStatus.getSelectedItem().toString());
                break;
            case "changeLocation":
                req.setLocation(etLocation.getText().toString());
                break;
            case "changeCurrentRider":
                req.setRiderId(Long.parseLong(etRiderId.getText().toString()));
                break;
            case "changeDeliveredDate":
                req.setDeliveredDate(etDeliveredDate.getText().toString());
                break;
        }
        Call<ResponseBody> c;
        switch(path) {
            case "changeStatus":
                c = api.changeStatus(req);
                break;
            case "changeLocation":
                c = api.changeLocation(req);
                break;
            case "changeCurrentRider":
                c = api.changeCurrentRider(req);
                break;
            case "changeDeliveredDate":
                c = api.changeDeliveredDate(req);
                break;
            default:
                throw new IllegalArgumentException("Unknown action "+path);
        }

        c.enqueue(new Callback<>() {
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
