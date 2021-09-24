package techtown.org.notificationscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // JobScheduler 처리를 위해 멤버변수 선언
    private JobScheduler mScheduler;
    private static final int JOB_ID = 0;

    // job options을 위한 switches 처리를 위해 멤버변수 선언
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;

    // deadline에 대한 seekBar 처리
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scheduleJob(View view) {
        mScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        RadioGroup networkOptions = findViewById(R.id.networkOptions);

        mDeviceIdleSwitch = findViewById(R.id.idleSwitch);
        mDeviceChargingSwitch = findViewById(R.id.chargingSwitch);

        mSeekBar = findViewById(R.id.seekBar);

        final TextView seekBarProgress = findViewById(R.id.seekBarProgress);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // seekBar 상태를 확인함
                if (i > 0) {
                    seekBarProgress.setText(i + " s");
                } else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;

        int selectedNetworkID = networkOptions.getCheckedRadioButtonId(); // 체크한 radio button ID 값 가져옴
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE; // 스케줄 중 네트워크 처리에 대한 옵션 디폴트 값 설정함

        // 체크한 Radio Button에 따라 분기를 다르게 처리함
        switch(selectedNetworkID) {
            case R.id.noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
                break;
        }

        // 앞서 만든 JobService에 대한 Notification 설정을 함
        ComponentName serviceName = new ComponentName(getPackageName(), NotificationJobService.class.getName());

        // Network에 따라 처리하기 위해서 선택한 Network 기점으로 JobInfo를 빌드하고 스케줄링 설정함
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(selectedNetworkOption)
                .setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked()) // user에 선택에 맞게 Switch에 대해 Builder가 체크가능함
                .setRequiresCharging(mDeviceChargingSwitch.isChecked());

        if (seekBarSet) {
            builder.setOverrideDeadline(seekBarInteger * 1000);
        }

        // 하나라도 constraint한 것이 있다면 true, 아니면 false로 설정함
        boolean constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE) || mDeviceChargingSwitch.isChecked() || mDeviceIdleSwitch.isChecked() || seekBarSet;

        if(constraintSet) {
            // job schedule을 하고 user에게 알림(true 일 경우 스케줄 처리)
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(this, "Job Scheduled, job will run when " + "the constraints are met.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please set at least one constraint", Toast.LENGTH_SHORT).show();
        }

        JobInfo myJobInfo = builder.build();
        mScheduler.schedule(myJobInfo);

        Toast.makeText(this, "Job Scheduled, job will run when " +
                "the constraints are met.", Toast.LENGTH_SHORT).show();

    }

    public void cancelJobs(View view) {

        // JobScheduler에 대한 널 체크를 하고 처리함
        if (mScheduler!=null){
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(this, "Jobs cancelled", Toast.LENGTH_SHORT).show();
        }

    }
}