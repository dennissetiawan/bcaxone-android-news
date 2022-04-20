package com.example.bcaxone_android_news;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class SessionManagement {
    //TODO: implement session token in login session
    public static final String SESSION_PREFERENCE = "com.example.bcaxone_android_news.SessionManagement.SESSION_PREFERENCE";
    public static final String SESSION_TOKEN = "com.example.bcaxone_android_news.SessionManagement.SESSION_TOKEN";
    public static final String SESSION_EXPIRE_TIME = "com.example.bcaxone_android_news.SessionManagement.SESSION_EXPIRE_TIME";

    private static SessionManagement INSTANCE;
    public static SessionManagement getInstance(){
        if(INSTANCE==null){
            INSTANCE = new SessionManagement();
        }
        return INSTANCE;
    }

    public void startUserSession(Context context, int expiredIn,String userToken){
        Calendar calendar = Calendar.getInstance();
        Date userLogginTime = calendar.getTime();
        calendar.setTime(userLogginTime);
        calendar.add(Calendar.SECOND, expiredIn);

        Date expireTime = calendar.getTime();

        SharedPreferences sharedPreferences = context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putLong(SESSION_EXPIRE_TIME,expireTime.getTime());
        editor.putString(SESSION_TOKEN,userToken);
        editor.apply();
    }

    //TODO: implement with service
    public boolean isSessionActive(Context context, Date currentTime){
        Date sessionExpiredAt = new Date(context.getSharedPreferences(SESSION_PREFERENCE, Context.MODE_PRIVATE).getLong(SESSION_EXPIRE_TIME,0));
        return !currentTime.after(sessionExpiredAt);

    }

    public void endUserSession (Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION_PREFERENCE,Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();

    }
}
