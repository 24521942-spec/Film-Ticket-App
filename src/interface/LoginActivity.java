package com.example.yourapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextInputEditText emailInput, passwordInput;
    private MaterialButton btnLogin;
    private androidx.appcompat.widget.AppCompatTextView linkSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // đổi theo tên file XML của bạn

        // Bind views
        imageView = findViewById(R.id.imageView);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        btnLogin = findViewById(R.id.btnLogin);
        linkSignUp = findViewById(R.id.linkSignUp);

        // Load ảnh nếu chưa set sẵn trong XML
        imageView.setImageResource(R.drawable.avatar);

        // Xử lý nút Login
        btnLogin.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            } else {
                // TODO: Xử lý đăng nhập (API call hoặc kiểm tra local)
                Toast.makeText(LoginActivity.this, "Logging in with " + email, Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý Sign Up link
        linkSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
