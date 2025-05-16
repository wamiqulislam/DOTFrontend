package com.example.dotfrontend.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dotfrontend.R;
import com.example.dotfrontend.api.ApiClient;
import com.example.dotfrontend.api.ApiService;


public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonSubmit;

    private Button buttontosignup;
    private Button buttontologin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MainActivity","onCreate runs");

        buttontosignup = findViewById(R.id.button_signup);
        buttontologin = findViewById(R.id.button_login);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        /*buttonSubmit.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            User user = new User(name,"exampleemal","Phone", "abcpassword", "Customer");


            Call<User> call = apiService.addUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Log.d("API", "User added: " + response.body().getName());
                        Toast.makeText(MainActivity.this, "User added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("API", "Error: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("API", "Failed: " + t.getMessage());
                }
            });

        });*/


        buttontosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttontologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}
