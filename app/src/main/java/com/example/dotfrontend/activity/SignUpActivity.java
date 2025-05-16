package com.example.dotfrontend.activity;

import android.content.Intent;
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
import com.example.dotfrontend.extras.AccountType;
import com.example.dotfrontend.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText etName, etEmail, etPhone, etPassword;
    private Spinner spinnerAccountType;
    private Button btnSignUp;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etPassword = findViewById(R.id.etPassword);
        spinnerAccountType = findViewById(R.id.spinnerAccountType);
        btnSignUp = findViewById(R.id.btnSignUp);

        // Spinner: load from res/values/strings.xml → <string-array name="account_type_array">
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.account_type_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccountType.setAdapter(adapter);

        // Retrofit service
        apiService = ApiClient.getClient().create(ApiService.class);

        btnSignUp.setOnClickListener(v -> attemptSignUp());
    }

    private void attemptSignUp() {
        String name     = etName.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();
        String phone    = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String sel      = spinnerAccountType.getSelectedItem().toString();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        AccountType type;
        if (sel.equalsIgnoreCase("RIDER")) {
            type = AccountType.Rider;
        } else if (sel.equalsIgnoreCase("CUSTOMER")) {
            type = AccountType.Customer;
        } else{
            type = AccountType.Admin;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        user.setAccountType(type);

        Call<ResponseBody> call = apiService.signUp(user);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<ResponseBody> c, Response<ResponseBody> resp) {
                if (resp.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Registered!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // try to read error body for "Email is already in Use"
                    String err = "Error " + resp.code();
                    Toast.makeText(SignUpActivity.this, err, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> c, Throwable t) {
                Toast.makeText(SignUpActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
