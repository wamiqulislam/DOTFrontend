// com.example.dotfrontend.util.SessionManager.java
package com.example.dotfrontend.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREFS_NAME = "user_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_ROLE    = "user_role";

    private SharedPreferences prefs;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveSession(Long userId, String role) {
        prefs.edit()
                .putLong(KEY_USER_ID, userId)
                .putString(KEY_ROLE, role)
                .apply();
    }

    public Long getUserId() {
        return prefs.getLong(KEY_USER_ID, -1L);
    }

    public String getUserRole() {
        return prefs.getString(KEY_ROLE, null);
    }

    public void clearSession() {
        prefs.edit().clear().apply();
    }
}
