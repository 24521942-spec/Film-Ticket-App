package com.example.ticketbookingapp.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketbookingapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText nameInput, emailInput, dobInput, phoneInput, passwordInput;
    private Spinner countryCodeSpinner;
    private MaterialButton btnRegister;
    private TextView linkLogin;

    private final int REGISTER_LAYOUT_RES_ID = R.layout.activity_register;

    private final Class<LoginActivity> LOGIN_ACTIVITY_CLASS = LoginActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(REGISTER_LAYOUT_RES_ID);

        initializeViews();

        setupCountryCodeSpinner();

        setupListeners();
    }

    private void initializeViews() {
        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        dobInput = findViewById(R.id.dobInput);
        phoneInput = findViewById(R.id.phoneInput);
        passwordInput = findViewById(R.id.passwordInput);
        countryCodeSpinner = findViewById(R.id.countryCodeSpinner);
        btnRegister = findViewById(R.id.btnRegister);
        linkLogin = findViewById(R.id.linkLogin);
    }

    private void setupCountryCodeSpinner() {
        String[] countryCodes = {"+84 VN", "+1 US", "+44 UK", "+81 JP"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                countryCodes
        );
        countryCodeSpinner.setAdapter(adapter);
    }

    private void setupListeners() {
        btnRegister.setOnClickListener(v -> registerUser());

        linkLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LOGIN_ACTIVITY_CLASS);
            startActivity(intent);
            finish();
        });

        dobInput.setOnClickListener(v -> showDatePicker());
        dobInput.setFocusable(false);
        dobInput.setCursorVisible(false);
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
                    dobInput.setText(date);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void registerUser() {
        String fullName = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String dob = dobInput.getText().toString().trim();
        String phone = phoneInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String countryCode = (String) countryCodeSpinner.getSelectedItem();

        if (fullName.isEmpty() || email.isEmpty() || dob.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            passwordInput.setError("Mật khẩu phải có ít nhất 6 ký tự.");
            passwordInput.requestFocus();
            return;
        }

        String fullPhoneNumber = countryCode + " " + phone;
        Toast.makeText(this, "Đăng ký thành công! SĐT: " + fullPhoneNumber, Toast.LENGTH_LONG).show();

        // TODO: Triển khai logic gọi API đăng ký và xử lý phản hồi tại đây
    }
}