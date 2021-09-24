package techtown.org.download;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Job스케줄러 사용을 위한 상수값 정의
    private static final int JOB_ID = 0;
    NotificationManager mNotifyManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
    }

    // 26 버전 이상은 채널을 먼저 만들고 처리해야함
    private void createNotificationChannel() {
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, getResources().getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(getResources().getString(R.string.channel_desc));
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    // 버튼을 누르고 Notification 생성을 위한 메소드
    void sendNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getResources().getString(R.string.notify_title))
                .setContentText(getResources().getString(R.string.notify_text))
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_download)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);
        mNotifyManager.notify(0, builder.build());
    }

    public void scheduleDowload(View view) {
        // JobScheduler 설정을 함
        sendNotification();
        JobScheduler mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(getApplicationContext(), DownloadService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
        builder.setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(true)
                .setPeriodic(TimeUnit.DAYS.toMillis(1));
        if (Build.VERSION.SDK_INT > 23) {
            builder.setPeriodic(TimeUnit.DAYS.toMillis(1), TimeUnit.MINUTES.toMillis(5));
        }
        JobInfo jobInfo = builder.build();
        if (mScheduler != null) {
            mScheduler.schedule(jobInfo);
        }
    }

    // Notification에 대한 정보를 PendingIntent를 통해 전달하므로 스케줄 시작시 보내게끔 JobService 활용함
    public class DownloadService extends JobService {
        @Override
        public boolean onStartJob(JobParameters params) {
            sendNotification();
            return false;
        }

        @Override
        public boolean onStopJob(JobParameters params) {
            return true;
        }
    }
}