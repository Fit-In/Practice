package techtown.org.standup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Alarm On,Off전에 알람을 나타낼 Notification 생성 필요
    private NotificationManager mNotificationManager;
    // notification ID와 CHANNEL 필요함
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        createNotificationChannel();

        // Alarm 등록을 위해 인텐트 처리하고 불러옴
        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);




        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);
        // alarm 상태를 확인하기 위한 값
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                String toastMessage;
                // Toggle Button 상태에 따라 boolean 값 다르게 설정함
                if(isChecked) {
                    // oncase일 경우, 버튼 누르면 notification이 나오고 알람이 설정됨
                    // 알람 설정의 경우 AlarmManager를 직접 사용하며 함
                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
                    long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;
                    // 시간은 상대 시간으로 설정하고 15분 마다 알람이 울리게끔 설정한 triggertime, 그리고 주기는 15분으로 설정하고 pendingIntent를 전달함
                    if(alarmManager != null) {
                        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                triggerTime, repeatInterval, notifyPendingIntent);
                    }
                    toastMessage = "Stand Up Alarm On!";
                } else {
                    // offcase일 경우, 알람을 다 꺼버림
                    if(alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }

                    toastMessage = "Stand Up Alarm Off!";
                }
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 26 버전 이상에서는 notification 사용하기 위해서 채널이 필요하므로 채널 생성 메소드 필요
    public void createNotificationChannel() {

        // notificationmanager 객체 생성
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // OREO 버전 이상에서만 채널이 필요하므로 조건문을 통해 설정함
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // notification channel을 만듬
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }


}