package com.example.ticketbookingapp.screens;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ticketbookingapp.R;
import com.example.ticketbookingapp.network.ApiClient;
import com.example.ticketbookingapp.network.ApiService;
import com.example.ticketbookingapp.network.dto.AuthResponse;
import com.example.ticketbookingapp.network.dto.RegisterRequest;
import com.example.ticketbookingapp.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText fullNameInput, emailInput, dobInput, phoneInput, passwordInput, confirmPasswordInput;
    private MaterialAutoCompleteTextView genderInput; // ✅ add
    private MaterialButton btnRegister;
    private TextView linkSignIn;
    private ImageButton btnBack;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        session = new SessionManager(this);

        // inputs
        fullNameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        dobInput = findViewById(R.id.dobInput);
        passwordInput = findViewById(R.id.passwordInput);

        // nếu XML bạn chưa có phoneInput thì có thể comment 2 dòng này
        // hoặc bạn thêm field phone vào XML tương ứng id phoneInput
        phoneInput = findViewById(R.id.phoneInput); // ✅ bạn đang dùng phoneLayout/phoneInput trong XML

        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        // ✅ Gender dropdown
        genderInput = findViewById(R.id.genderInput);
        setupGenderDropdown();

        // buttons/links
        btnRegister = findViewById(R.id.btnRegister);
        linkSignIn = findViewById(R.id.linkLogin);
        btnBack = findViewById(R.id.btnBack);

        // ✅ Back về Home
        btnBack.setOnClickListener(v -> goHome());

        // link login
        linkSignIn.setOnClickListener(v -> finish());

        btnRegister.setOnClickListener(v -> doRegister());

        // ✅ Bấm back hệ thống cũng về Home (nếu bạn muốn)
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override public void handleOnBackPressed() {
                goHome();
            }
        });

        // (Tuỳ chọn) DOB picker cho đẹp
        setupDobPicker();
    }

    private void setupGenderDropdown() {
        if (genderInput == null) return;

        String[] genders = {"Nam", "Nữ"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                genders
        );
        genderInput.setAdapter(adapter);

        // Tuỳ chọn: set mặc định
        // genderInput.setText("Nam", false);

        // Tuỳ chọn: click vào là xổ dropdown luôn
        genderInput.setOnClickListener(v -> genderInput.showDropDown());
    }

    private void goHome() {
        Intent i = new Intent(RegisterActivity.this, HomeActivity.class);

        // ✅ tránh tạo nhiều Home trong backstack + clear các màn auth
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        // Nếu Home chưa có trong stack thì sẽ tạo mới, nhưng vẫn clear auth
        // Bạn có thể dùng thêm NEW_TASK/CLEAR_TASK nếu muốn reset sạch:
        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(i);
        finish();
    }

    private void doRegister() {
        String fullName = textOf(fullNameInput);
        String email = textOf(emailInput);
        String dob = textOf(dobInput);     // "2000-01-01"
        String phone = phoneInput == null ? "" : textOf(phoneInput);
        String password = textOf(passwordInput);
        String confirmPassword = textOf(confirmPasswordInput);

        // ✅ lấy gender từ dropdown
        String genderVN = genderInput == null ? "" : genderInput.getText().toString().trim();
        String genderApi = "male"; // default
        if ("Nữ".equalsIgnoreCase(genderVN)) genderApi = "female";
        if ("Nam".equalsIgnoreCase(genderVN)) genderApi = "male";

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
        if (!password.equals(confirmPassword)) {
            toast("Confirm password không khớp");
            return;
        }
        if (!dob.isEmpty() && !dob.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            toast("DOB sai định dạng (yyyy-MM-dd)");
            return;
        }
        if (!phone.isEmpty() && phone.length() < 8) {
            toast("Số điện thoại không hợp lệ");
            return;
        }
        // ✅ bắt buộc chọn gender (nếu bạn muốn bắt buộc)
        if (genderVN.isEmpty()) {
            toast("Vui lòng chọn giới tính");
            return;
        }

        btnRegister.setEnabled(false);

        ApiService api = ApiClient.getClient(this).create(ApiService.class);
        RegisterRequest req = new RegisterRequest();
        req.setFullName(fullName);
        req.setEmail(email);
        req.setDob(dob);                 // "2000-01-01"
        req.setPassword(password);

        // ✅ dùng dữ liệu user nhập (fallback nếu rỗng)
        req.setPhone(phone.isEmpty() ? "0123456789" : phone);
        req.setGender(genderApi);
        req.setAddress("HCM");           // default (nếu bạn có addressInput thì mình nối tiếp)

        api.register(req).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnRegister.setEnabled(true);

                if (!response.isSuccessful()) {
                    toast("Register fail: " + response.code());
                    return;
                }

                AuthResponse body = response.body();
                if (body != null && body.getToken() != null && !body.getToken().isEmpty()) {
                    session.saveLogin(
                            body.getToken(),
                            body.getUserId(),
                            body.getFullName(),
                            body.getEmail(),
                            body.getRole()
                    );
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    finish();
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

    private void setupDobPicker() {
        if (dobInput == null) return;

        dobInput.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(
                    RegisterActivity.this,
                    (view, year, month, dayOfMonth) -> {
                        String s = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
                        dobInput.setText(s);
                    },
                    y, m, d
            );
            dialog.show();
        });
    }

    private String textOf(TextInputEditText e) {
        return e == null || e.getText() == null ? "" : e.getText().toString().trim();
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
