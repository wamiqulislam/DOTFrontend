package com.example.dotfrontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dotfrontend.R;
import com.example.dotfrontend.util.SessionManager;

public class CustomerInterfaceActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_interface);

        // init session and show user info
        session = new SessionManager(this);
        TextView userInfo = findViewById(R.id.text_user_info);
        long userId = session.getUserId();
        String role = session.getUserRole();
        userInfo.setText("Welcome, " + role + " (ID: " + userId + ")");

        // button listeners
        findViewById(R.id.button_view_parcel_status).setOnClickListener(v ->
                startActivity(new Intent(this, ViewParcelActivity.class))
        );
        findViewById(R.id.button_send_parcel).setOnClickListener(v ->
                startActivity(new Intent(this, SendParcelActivity.class))
        );
    }
}
