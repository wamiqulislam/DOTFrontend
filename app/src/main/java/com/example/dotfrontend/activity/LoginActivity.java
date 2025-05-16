package com.example.dotfrontend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;
import com.example.dotfrontend.extras.AccountType;
import com.example.dotfrontend.model.User;
import com.example.dotfrontend.util.SessionManager;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;      // make sure this is retrofit2.Callback!
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ApiService api;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session   = new SessionManager(this);
        api       = ApiClient.getClient().create(ApiService.class);

        etEmail   = findViewById(R.id.etEmail);
        etPassword= findViewById(R.id.etPassword);
        Button btn = findViewById(R.id.btnLogin);
        btn.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String email = etEmail.getText().toString().trim();
        String pass  = etPassword.getText().toString().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String,String> creds = new HashMap<>();
        creds.put("email", email);
        creds.put("password", pass);

        // 1) First call: just checks true/false
        api.login(creds).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> resp) {
                if (resp.isSuccessful() && Boolean.TRUE.equals(resp.body())) {
                    // 2) Immediately fetch full User by email
                    api.getUserFromEmail(email).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> c2, Response<User> r2) {
                            if (r2.isSuccessful() && r2.body() != null) {
                                User me = r2.body();
                                // 3) Save into SharedPreferences
                                session.saveSession(
                                        me.getUserId(),
                                        me.getAccountType().name()
                                );
                                // 4) Go to Respective interface
                                if (me.getAccountType() == AccountType.Rider){
                                    startActivity(new Intent(LoginActivity.this, RiderInterfaceActivity.class));
                                }else if (me.getAccountType() == AccountType.Customer){
                                    startActivity(new Intent(LoginActivity.this, CustomerInterfaceActivity.class));
                                }
                                else {
                                    startActivity(new Intent(LoginActivity.this, AdminInterfaceActivity.class));
                                }
                            } else {
                                Toast.makeText(
                                        LoginActivity.this,
                                        "Failed to load user data",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<User> c2, Throwable t2) {
                            Toast.makeText(
                                    LoginActivity.this,
                                    "Error fetching profile: " + t2.getMessage(),
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(LoginActivity.this,
                        "Login error: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
