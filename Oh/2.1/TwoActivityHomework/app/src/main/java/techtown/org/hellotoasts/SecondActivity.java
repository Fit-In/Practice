package techtown.org.hellotoasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView mShowResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mShowResult = findViewById(R.id.show_result);
        // count를 intent로 넘겼으므로 해당하는 값을 받음
        Intent intent = getIntent();
        // 받은 인텐트 값을 가지고 디폴트 설정도 마침
        int Count = intent.getIntExtra(MainActivity.EXTRA_TEXT,0);
        // 결과값을 출력하기 위해서 String으로 변환하고 결과를 출력함
        mShowResult.setText("Hello\n" + Integer.toString(Count));
    }
}