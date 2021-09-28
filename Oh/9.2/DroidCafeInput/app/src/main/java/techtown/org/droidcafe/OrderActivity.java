package techtown.org.droidcafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// Spinner를 활용하기 위해서 AdapterView에 해당하는 인터페이스를 구현함
public class OrderActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // 인텐트로 넘긴 값 받아서 TextView에 띄우는 작업
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.order_textview);
        textView.setText(message);


    }

    public void onRadioButtonClicked(View view) {
        // RadioButton이 체크되어 있는지 확인함
        boolean checked = ((RadioButton) view).isChecked();
        // 체크한 상태에 따라서 이벤트 처리ㅡㄹㄹ 함
        switch(view.getId()) {
            case R.id.sameday:
                if (checked)
                    displayToast(getString(R.string.same_day_messenger_service));
                break;
            case R.id.nextday:
                if (checked)
                    displayToast(getString(R.string.next_day_ground_delivery));
                break;
            case R.id.pickup:
                if (checked)
                    displayToast(getString(R.string.pick_up));
                break;
            default:
                break;
        }
    }

    // 토스트 메시지를 띄우기 위한 메소드
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }


}