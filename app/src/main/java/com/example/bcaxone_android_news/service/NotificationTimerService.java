package com.example.bcaxone_android_news.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.bcaxone_android_news.MainActivity;
import com.example.bcaxone_android_news.R;

public class NotificationTimerService extends Service {
    public static final String SERVICE_NOTIFICATION_NEWS_ID = "com.example.bcaxone_android_news.service.NotificationTimerService.SERVICE_NOTIFICATION_NEWS_ID";
    public static final String SERVICE_NOTIFICATION_NEWS_TITLE = "com.example.bcaxone_android_news.service.NotificationTimerService.SERVICE_NOTIFICATION_NEWS_TITLE";
    private static final String myChannelId = "myChannelId";
    private static final String myChannel = "myChannel";
    private NotificationManager notificationManager;
    private String articleTitle;
    private int articleId;
    String TAG = "NotificationTimerService";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                Log.d(TAG,""+l);

            }

            @Override
            public void onFinish() {
                Log.d(TAG,"onFinish");
                setupNotification();
                stopSelf();
            }
        };
        countDownTimer.start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        articleId = intent.getIntExtra(SERVICE_NOTIFICATION_NEWS_ID,0);
        articleTitle = intent.getStringExtra(SERVICE_NOTIFICATION_NEWS_TITLE);
        return super.onStartCommand(intent, flags, startId);
    }

    private void setupNotification() {
        NotificationCompat.Builder builder = createNotification().addAction(R.drawable.icons8_us_news_100,"Read Now",addActionOK());
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(222,builder.build());
    }

    private PendingIntent addActionOK() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(SERVICE_NOTIFICATION_NEWS_ID,articleId);
        int flag = PendingIntent.FLAG_CANCEL_CURRENT;
        return PendingIntent.getActivity(this,1111,intent,flag);
    }

    private NotificationCompat.Builder createNotification() {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ) {
            //can use notification channel
            createChannelNotification();
            Log.d("TimeService", "Versi O");

            builder = new NotificationCompat.Builder(this,myChannelId)
                    .setSmallIcon(R.drawable.newslogo)
                    .setContentTitle("Read News Reminder")
                    .setContentText("Read "+articleTitle+" now");
        } else {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.newslogo)
                    .setContentTitle("Read News Reminder")
                    .setContentText("Read "+articleTitle+" now");
        }
        return builder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannelNotification() {
        NotificationChannel channel = new NotificationChannel(myChannelId, myChannel, NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
