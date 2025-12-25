package com.example.ticketbookingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {
    private static final String PREF = "user_session";
    private static final String KEY_ID = "userId";
    private static final String KEY_NAME = "fullName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    private final SharedPreferences sp;

    public UserSession(Context ctx) {
        sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void save(int userId, String fullName, String email, String role) {
        sp.edit()
                .putInt(KEY_ID, userId)
                .putString(KEY_NAME, fullName)
                .putString(KEY_EMAIL, email)
                .putString(KEY_ROLE, role)
                .apply();
    }

    public boolean isLoggedIn() {
        return sp.getInt(KEY_ID, -1) != -1;
    }

    public int getUserId() { return sp.getInt(KEY_ID, -1); }
    public String getFullName() { return sp.getString(KEY_NAME, ""); }
    public String getEmail() { return sp.getString(KEY_EMAIL, ""); }
    public String getRole() { return sp.getString(KEY_ROLE, ""); }

    public void clear() {
        sp.edit().clear().apply();
    }
}
