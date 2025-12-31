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
import com.example.ticketbookingapp.network.dto.LoginRequest;
import com.example.ticketbookingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private MaterialButton btnLogin;
    private TextView linkRegister;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);
        linkRegister = findViewById(R.id.linkSignUp);

        linkRegister.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class))
        );

        btnLogin.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String email = textOf(emailInput).toLowerCase();
        String password = textOf(passwordInput);

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Email không hợp lệ");
            return;
        }
        if (password.length() < 6) {
            toast("Password tối thiểu 6 ký tự");
            return;
        }

        btnLogin.setEnabled(false);

        ApiService api = ApiClient.getClient(this).create(ApiService.class);
        api.login(new LoginRequest(email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnLogin.setEnabled(true);

                if (!response.isSuccessful() || response.body() == null) {
                    toast("Login fail: " + response.code());
                    return;
                }

                AuthResponse body = response.body();

                // Token bắt buộc để gọi profile/booking...
                if (body.getToken() == null || body.getToken().isEmpty()) {
                    toast("Login fail: token null");
                    return;
                }

                session.saveLogin(
                        body.getToken(),
                        body.getUserId(),
                        body.getFullName(),
                        body.getEmail(),
                        body.getRole()
                );

                toast("Login OK");
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
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
