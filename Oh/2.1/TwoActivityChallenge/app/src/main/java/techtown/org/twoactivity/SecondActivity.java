package techtown.org.twoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textView = findViewById(R.id.text_message);
        Intent intent = getIntent(); // 인텐트를 보냈기 때문에 인텐트를 받음
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); // 받은 인텐트 중 EditText에 있는걸 받기 위해서 Main에서 선언한 키 값으로 받음
        // TextView를 찾고 인텐트로 받은 String을 설정함
        textView.setText(message);
    }


}