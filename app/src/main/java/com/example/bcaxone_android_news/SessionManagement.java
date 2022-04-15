package com.example.bcaxone_android_news;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class SessionManagement {
    public static final String SESSION_PREFERENCE = "om.example.bcaxone_android_news.SessionManagement.SESSION_PREFERENCE";
    public static final String  SESSION_TOKEN = "om.example.bcaxone_android_news.SessionManagement.SESSION_TOKEN";
    public static final String  SESSION_EXPIRE_TIME = "om.example.bcaxone_android_news.SessionManagement.SESSION_EXPIRE_TIME";

    private static SessionManagement INSTANCE;
    public static SessionManagement getINSTANCE(){
        if(INSTANCE==null){
            INSTANCE = new SessionManagement();
        }
        return INSTANCE;
    }

    public void startUserSession(Context context, int expiredIn){
        Calendar calendar = Calendar.getInstance();
        Date userLogginTime = calendar.getTime();
        calendar.setTime(userLogginTime);
        calendar.add(Calendar.SECOND, expiredIn);
        Date expireTime = calendar.getTime();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(SESSION_EXPIRE_TIME,expireTime.getTime());
        editor.apply();
    }

    public boolean idSessionActive(Context context, Date currentTime){
        Date sessionExpiredAt = new Date(context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).getLong(SESSION_EXPIRE_TIME,0));
        return !currentTime.after(sessionExpiredAt);

    }

    public void endUserSession (Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE,Context.MODE_PRIVATE);
        sharedPreferences.edit().clear();
        sharedPreferences.edit().apply();

    }
}
