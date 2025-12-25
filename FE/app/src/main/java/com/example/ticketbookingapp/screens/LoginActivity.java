package com.example.ticketbookingapp.screens;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailInput, passwordInput;
    private MaterialButton btnLogin;
    private TextView linkSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);
        linkSignUp = findViewById(R.id.linkSignUp);

        linkSignUp.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        btnLogin.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String email = textOf(emailInput);
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

        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.login(new LoginRequest(email, password)).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnLogin.setEnabled(true);

                if (!response.isSuccessful() || response.body() == null) {
                    toast("Login fail: " + response.code());
                    return;
                }

                AuthResponse user = response.body();
                saveUser(user);

                toast("Xin chào " + user.getFullName());

                // chuyển về Home (đổi đúng Activity bạn đang dùng)
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                toast("Error: " + t.getMessage());
            }
        });
    }

    private void saveUser(AuthResponse user) {
        SharedPreferences sp = getSharedPreferences("auth", MODE_PRIVATE);
        sp.edit()
                .putInt("userId", user.getUserId())
                .putString("fullName", user.getFullName())
                .putString("email", user.getEmail())
                .putString("role", user.getRole())
                .apply();
    }

    private String textOf(TextInputEditText e) {
        return e.getText() == null ? "" : e.getText().toString().trim();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
