package techtown.org.notifyme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // Notification 처리를 위해서 채널이 필요함, 그러므로 채널 ID 상수선언
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager; // Notification 만들기 위한 manager 생성

    // 유저가 notification 안에서 update를 하기 위한 action button 처리
    private static final String ACTION_UPDATE_NOTIFICATION = BuildConfig.APPLICATION_ID + ".ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION = BuildConfig.APPLICATION_ID + ".ACTION_CANCEL_NOTIFICATION";
    private NotificationReceiver mReceiver = new NotificationReceiver();

    // Notification을 위한 ID 필요함
    private static final int NOTIFICATION_ID = 0;

    private Button button_notify;
    private Button button_cancel;
    private Button button_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();

        // 상황에 동적으로 확인하기 위해서 UPDATE, CANCEL에 대해서 Receiver를 통해서 받아서 처리함
        // 그때 update, cancel 두 가지 상황 다 처리하기 위해서 intentfilter를 사용함
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        // actionbutton 처리 위해 notification을 Receiver에 등록함
        registerReceiver(mReceiver, intentFilter);

        // notification을 나타내기 위한 버튼과 클릭 리스너 처리함
        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendNotification();
            }
        });

        // update, cancel에 대해서 notification 만듬
        button_update = findViewById(R.id.update);
        button_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                updateNotification();
            }
        });

        button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                cancelNotification();
            }
        });

        setNotificationButtonState(true, false, false);
    }



    // 채널을 만드는 메소드
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // API 26버전 이상에선 채널을 만들어야 하므로 해당 버전 조건을 체크함
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // 26버전 이상이므로 채널을 만듬
            // channel ID는 위에서 선언한 상수로 하고, 채널 이름, 중요도 설정함
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Mascot Notification", NotificationManager.IMPORTANCE_HIGH);

            // 채널에 대한 다양한 설정을 할 수 있음
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    // Notification을 만드는 메소드
    private NotificationCompat.Builder getNotificationBuilder() {
        // PendingIntent는 notification에 대한 action을 취하게 해줌, 이 처리까지 해줘야 Notification와 누르고 상호작용이 가능함
        // PendingIntent 처리하기 전에 Intent 처리
        Intent notificationIntent = new Intent(this, MainActivity.class);
        // PendingIntent 처리, requestCode와 flag 설정 필요함
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Notification Builder를 통해서 생성할 Notification을 만듬
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent) // pendingIntent 처리, 눌렀을 때 인텐트
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 중요도 설정
                .setDefaults(NotificationCompat.DEFAULT_ALL); // Notification 나올때 디폴트 설정

        return notifyBuilder;
    }

    // 앞서 선언한 메소드를 통해서 Notification 만듬
    public void sendNotification() {
        // BroadcastReceiver로 하기 위해서 Intent 처리
        Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        // 그리고 update를 Notification에 하기 위해 Action을 추가함
        notifyBuilder.addAction(R.drawable.ic_update, "Update Notification", updatePendingIntent);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, true, true);
    }

    public void updateNotification() {
        // 사진을 업데이트 하기 위해 BitMap으로 그림
        Bitmap androidImgae = BitmapFactory.decodeResource(getResources(),R.drawable.mascot_1);
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
//        // Notification update를 위해 기존 notification을 변경(BigPictureStyle, Task)
//        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
//        .bigPicture(androidImgae)
//        .setBigContentTitle("Notification Updated!"));
        // 위처럼 BigPictureStyle이 아닌 InboxStyle로 바꿈(Homework)
        notifyBuilder.setLargeIcon(androidImgae)
                .setStyle(new NotificationCompat.InboxStyle()
                .addLine(getString(R.string.first_line))
                .addLine(getString(R.string.second_line))
                .setBigContentTitle(getString(R.string.title))
                .setSummaryText(getString(R.string.summary_text)));
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        setNotificationButtonState(false, false, true);

    }

    public void cancelNotification() {
        // Notification이 cancel됨
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true, false, false);
    }

    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        // 3개의 버튼의 상태를 누른 상태에 따라 관리하기 위한 메소드, 각각 버튼이 눌린 시점에 boolean 값 업데이트 하면 됨
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabled);
        button_cancel.setEnabled(isCancelEnabled);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver); // 등록해제
        super.onDestroy();
    }

    public class NotificationReceiver extends BroadcastReceiver {
        // 유저가 notification 안에서 update를 하기 위한 action button 처리, 그러기 위해서 Notification을 BroadcastReceiver로 받아야함

        public NotificationReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String intentAction = intent.getAction();
            if(intentAction != null) {
                // intent Action으로 받아서 update, cancel의 분기에 따라 다르게 처리함
                switch(intentAction) {
                    case ACTION_UPDATE_NOTIFICATION: updateNotification(); break;
                    case ACTION_CANCEL_NOTIFICATION: setNotificationButtonState(true, false, false);
                    default: break;
                }
            }
        }
    }
}

