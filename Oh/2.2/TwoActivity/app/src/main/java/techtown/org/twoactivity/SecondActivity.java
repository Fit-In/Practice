package techtown.org.twoactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    // 다시 메인으로 보내기 위한 키 값을 상수로 설정함
    public static final String EXTRA_REPLY = "techtwon.org.twoactivity.extra.REPLY";

    private EditText mReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mReply = findViewById(R.id.editText_second);

        Intent intent = getIntent(); // 인텐트를 보냈기 때문에 인텐트를 받음
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE); // 받은 인텐트 중 EditText에 있는걸 받기 위해서 Main에서 선언한 키 값으로 받음
        // TextView를 찾고 인텐트로 받은 Stringd을 설정함
        TextView textView = findViewById(R.id.text_message);
        textView.setText(message);
    }

    public void returnReply(View view) {
        // 답장을 보내기 위해 이 액티비티에서 입력한 문자를 String으로 받아옴
        String reply = mReply.getText().toString();
        // 동일하게 인텐트 선언후 답장한 입력 문자열을 보냄
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        // 받은 인텐트에 대해서 답장 형식으로 다시 보내므로 인텐트를 원활하게 받았음을 알리는 RESULT_OK로 설정하고 답장 데이터를 보냄
        setResult(RESULT_OK, replyIntent);
        finish(); // 정상적으로 받고 답장을 보냈으므로 현재 액티비티를 닫고 메인으로 돌아감
    }
}