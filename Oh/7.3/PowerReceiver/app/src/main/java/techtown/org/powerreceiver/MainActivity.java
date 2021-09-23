package techtown.org.powerreceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // BroadcastReceiver를 사용하기 위해 초기화
    private CustomReceiver mReceiver = new CustomReceiver();

    // Broadcast Intent action을 위해 사용
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private static final String RANDOM_NUMBER = BuildConfig.APPLICATION_ID + ".RANDOM_NUMBER";

    // View 초기화
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // Broadcast를 Intent로 받을 때, IntentFilter를 통해서 receivers의 특정 value를 필터링할 수 있음
        IntentFilter filter = new IntentFilter();

        // Power와 관련한 것을 받을 것이므로 추가함
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);

        // 현재 액티비티에 receiver를 등록함, filter된 broadcast만을 체킹, 등록함
        this.registerReceiver(mReceiver, filter);

        // LocalBroadcast에 등록함
        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        // 앱이 꺼질때 등록을 해제함
        this.unregisterReceiver(mReceiver);
        // 등록해제
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    public void sendCustomBroadcast(View view) {
        // LocalBroadcast로 활용할 예정, broadcast를 등록하거나 보낼때 활용됨(앱 안에 컴포넌트에 활용)
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);
        // random 숫자 생성
        int num = randomNum();
        textView.setText(String.format("Number is %s", String.valueOf(num)));
        // 인텐트로 randomnumber 보냄
        customBroadcastIntent.putExtra(RANDOM_NUMBER, String.valueOf(num));
        // LocalBroadcast에 보냄
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);

    }

    public int randomNum() {
        Random random = new Random();
        return random.nextInt(20) + 1;
    }
}