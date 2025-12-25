package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.AuthResponse;
import com.example.ticketbookingapp.network.dto.RegisterRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fullNameInput, emailInput, passwordInput;
    private MaterialButton btnRegister;
    private TextView linkSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // ĐỔI ID cho đúng với activity_register.xml của bạn
        fullNameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnRegister = findViewById(R.id.btnRegister);
        linkSignIn = findViewById(R.id.linkLogin);

        linkSignIn.setOnClickListener(v -> finish());
        btnRegister.setOnClickListener(v -> doRegister());
    }

    private void doRegister() {
        String fullName = textOf(fullNameInput);
        String email = textOf(emailInput);
        String password = textOf(passwordInput);

        if (fullName.length() < 2) {
            toast("Full name quá ngắn");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Email không hợp lệ");
            return;
        }
        if (password.length() < 6) {
            toast("Password tối thiểu 6 ký tự");
            return;
        }

        btnRegister.setEnabled(false);

        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.register(new RegisterRequest(fullName, email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnRegister.setEnabled(true);

                if (!response.isSuccessful() || response.body() == null) {
                    toast("Register fail: " + response.code());
                    return;
                }

                toast("Register OK. Mời đăng nhập!");
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnRegister.setEnabled(true);
                toast("Error: " + t.getMessage());
            }
        });
    }

    private String textOf(TextInputEditText e) {
        return e.getText() == null ? "" : e.getText().toString().trim();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
