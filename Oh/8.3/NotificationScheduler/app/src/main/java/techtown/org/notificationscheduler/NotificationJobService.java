package techtown.org.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

/*
개발자가 정의하는 작업을 스케줄링 해주는 JobService 클래스, 백그라운드에서 작업을 하므로 효율도 좋음
 */

public class NotificationJobService extends JobService {

    NotificationManager mNotifyManager;

    // 채널 ID 설정
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    // 예약된 작업을 실행함
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        createNotificationChannel();

        // 앱을 클릭해서 실행시 PendingIntent 넘김
        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        // 그러면 Notification을 아래와 같이 만들어서 넘김
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (this, PRIMARY_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job ran to completion!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // 만약 job이 실패했다면 job을 reschedule하기 위해 true로 설정함
        return true;
    }

    // 채널 만드는 함수
    public void createNotificationChannel() {

        // NotificationManager 정의
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // 채널은 26버전 이상만 쓰므로 그 조건을 걸어둠
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // NotificationChannel을 만듬
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Job Service notification",
                    NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
