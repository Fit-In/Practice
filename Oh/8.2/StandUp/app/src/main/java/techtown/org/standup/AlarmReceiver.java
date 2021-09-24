package techtown.org.standup;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
/*
AlarmManager 역시 PendingIntent를 활용해서 세부 옵션을 전달받음
그래서 이를 Broadcast Receiver를 통해서 활용할 것임, 그래서 이를 바탕으로 AlarmManager를 사용하여 세부적인 알람에 대해서 셀프로 설정을 할 수 있음
 */

public class AlarmReceiver extends BroadcastReceiver {

    // Alarm On,Off전에 알람을 나타낼 Notification 생성 필요
    private NotificationManager mNotificationManager;
    // notification ID와 CHANNEL 필요함
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // broadcast Receiver에서 notification 받기 위해서 수정함
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        deliverNotification(context);
    }

    // Notification 생성을 위한 메소드 PendingIntent, Intent 처리함
    private void deliverNotification(Context context) {
        // 상호작용을 위해 먼저 Intent 처리를 함
        Intent contentIntent = new Intent(context, MainActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity(context,NOTIFICATION_ID,contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Notification 생성
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());

    }
}