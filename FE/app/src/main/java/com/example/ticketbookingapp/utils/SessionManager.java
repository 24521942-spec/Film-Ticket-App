package com.example.ticketbookingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF = "session";
    private static final String K_USER_ID = "userId";
    private static final String K_FULL_NAME = "fullName";
    private static final String K_EMAIL = "email";
    private static final String K_ROLE = "role";

    // ✅ thêm
    private static final String K_TOKEN = "token";

    private final SharedPreferences sp;

    public SessionManager(Context ctx) {
        sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    // ✅ giữ nguyên
    public void save(int userId, String fullName, String email, String role) {
        sp.edit()
                .putInt(K_USER_ID, userId)
                .putString(K_FULL_NAME, fullName)
                .putString(K_EMAIL, email)
                .putString(K_ROLE, role)
                .apply();
    }

    // ✅ thêm: lưu cả token (dùng cho JWT)
    public void saveLogin(String token, int userId, String fullName, String email, String role) {
        sp.edit()
                .putString(K_TOKEN, token)
                .putInt(K_USER_ID, userId)
                .putString(K_FULL_NAME, fullName)
                .putString(K_EMAIL, email)
                .putString(K_ROLE, role)
                .apply();
    }

    public boolean isLoggedIn() {
        return sp.getInt(K_USER_ID, -1) != -1;
    }

    // ✅ thêm getter
    public int getUserId() {
        return sp.getInt(K_USER_ID, -1);
    }

    public String getToken() {
        return sp.getString(K_TOKEN, null);
    }

    public String getFullName() {
        return sp.getString(K_FULL_NAME, "");
    }

    public String getEmail() {
        return sp.getString(K_EMAIL, "");
    }

    public String getRole() {
        return sp.getString(K_ROLE, "");
    }

    public void clear() {
        sp.edit().clear().apply();
    }
}
