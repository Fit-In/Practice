package techtown.org.standupcc;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void nextAlarm(View view) {
        // 다음 알람에 대해서 불러와서 볼 수 있음
        AlarmManager.AlarmClockInfo info = alarmManager.getNextAlarmClock();
        String message;
        // 알람을 불러와서 해당 Date에 대해서 뽑아와서 체크할 수 있음
        if(info != null) {
            Date nextAlarm = new Date(info.getTriggerTime());
            String newDate = DateFormat.getTimeInstance().format(nextAlarm);

            message = "The alarm is set for " + newDate + ".";
        } else {
            message = "There is no alarm scheduled.";
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}