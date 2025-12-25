package com.example.ticketbookingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF = "session";
    private static final String K_USER_ID = "userId";
    private static final String K_FULL_NAME = "fullName";
    private static final String K_EMAIL = "email";
    private static final String K_ROLE = "role";

    private final SharedPreferences sp;

    public SessionManager(Context ctx) {
        sp = ctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
    }

    public void save(int userId, String fullName, String email, String role) {
        sp.edit()
                .putInt(K_USER_ID, userId)
                .putString(K_FULL_NAME, fullName)
                .putString(K_EMAIL, email)
                .putString(K_ROLE, role)
                .apply();
    }

    public boolean isLoggedIn() {
        return sp.getInt(K_USER_ID, -1) != -1;
    }

    public void clear() {
        sp.edit().clear().apply();
    }
}
