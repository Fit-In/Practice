package techtown.org.twoactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // LOG_TAG, Logcat에서 확인할 때 해당 로그에 대해서 메시지를 확인하기 위해 일단 액티비티 이름으로 설정함
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // class를 정의한 키 값을 상수로 정의함
    public static final String EXTRA_MESSAGE = "techtown.org.twoactivity.extra.MESSAGE";
    // data를 돌려받기 위해 응답키를 상수로 정의함
    public static final int TEXT_REQUEST = 1;

    private EditText mMessageEditText;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageEditText = findViewById(R.id.editText_main);
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);
    }

    public void launchSecondActivity(View view) {
        // 버튼 누르면 로그에 해당 메시지가 찍힘
        Log.d(LOG_TAG, "ButtonClicked");
        // SecondActivity로 넘어가기 위한 인텐트 처리
        Intent intent = new Intent(this, SecondActivity.class);
        String message = mMessageEditText.getText().toString(); // EditText로 입력받은 값을 String으로 변환함
        intent.putExtra(EXTRA_MESSAGE,message); // 위에서 어떤 액티비티에서 넘어왔는지 확인하기 위한 상수 key와 message value 쌍으로 인텐트로 보냄
//        startActivity(intent); // 데이터를 돌려받지 않고 단순하게 다른 액티비티를 실행할때 씀
        startActivityForResult(intent, TEXT_REQUEST); // 데이터를 돌려받기 때문에 응답키까지 같이 보냄
    }

    // 결과를 리턴받기 위해서 오버라이드 함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // requestCode는 위에서 intent로 넘길때 같이 넘어온 값, resultCode는 SecondActivity에서 설정한 RESULT_OK 값, data는 second에서 다시 받는 데이터
        super.onActivityResult(requestCode, resultCode, data);
        // 응답키와 함께 넘긴 인텐트가 정상적으로 RESULT_OK로 다시 넘어온다면
        if(requestCode == TEXT_REQUEST){
            if(resultCode == RESULT_OK){
                // 응답코드로 잘 넘기고 잘 받아서 ok가 넘어오면 해당 인텐트에서 넘겨준 응답 값을 String으로 받음
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mReplyHeadTextView.setVisibility(View.VISIBLE); // 응답을 받기 전에 보이지 않은 것을 보이게 함
                mReplyTextView.setText(reply); // 응답 받은 값을 다시 받음
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}