package techtown.org.powerreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    // Broadcast Intent action을 위해 사용
    private static final String ACTION_CUSTOM_BROADCAST = BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";
    private static final String RANDOM_NUMBER = BuildConfig.APPLICATION_ID + ".RANDOM_NUMBER";

    @Override
    public void onReceive(Context context, Intent intent) {
        // broadcast를 가로채서 Intent를 통해 전달받아서 처리를 함
        String intentAction = intent.getAction();
        String stringNumber = intent.getStringExtra(RANDOM_NUMBER);
        // number를 받아옴
        int number = Integer.valueOf(stringNumber);
        // intentAction을 활용하기 위해 switch 분기문 활용함
        if(intentAction != null) {
            String toastMessage = "unknown intent action";
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected!";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast Received";
                    break;
            }

            Toast.makeText(context, toastMessage + "Square of number is " + number * number, Toast.LENGTH_SHORT).show();
        }
    }
}