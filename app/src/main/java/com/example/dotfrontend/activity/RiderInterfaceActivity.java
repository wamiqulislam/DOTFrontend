package com.example.dotfrontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dotfrontend.R;
import com.example.dotfrontend.util.SessionManager;

public class RiderInterfaceActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_interface);

        // init session and show user info
        session = new SessionManager(this);
        TextView userInfo = findViewById(R.id.text_user_info);
        long userId = session.getUserId();
        String role = session.getUserRole();
        userInfo.setText("Welcome, " + role + " (ID: " + userId + ")");

        // button listeners
        findViewById(R.id.button_register_vehicle).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterVehicleActivity.class))
        );
        findViewById(R.id.button_add_parcel).setOnClickListener(v ->
                startActivity(new Intent(this, SendParcelActivity.class))
        );
        findViewById(R.id.button_finish_delivery).setOnClickListener(v ->
                startActivity(new Intent(this, ChangeParcelActivity.class))
        );
        findViewById(R.id.button_choose_parcel).setOnClickListener(v ->
                startActivity(new Intent(this, ChooseParcelActivity.class))
        );
    }
}
