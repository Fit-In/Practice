package techtown.org.simpleasynctask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView; // 결과값을 보여줄 textview
    private ProgressBar mProgressBar;

    private static final String TEXT_STATE = "currentText"; // 현재 TextView값을 유지하기 위해 지정한 키값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView1);
        mProgressBar = findViewById(R.id.progressBar);

        // TextView의 상태를 저장한 값을 활용함
        if(savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));

        }
    }

    public void startTask(View view) {
        mTextView.setText(R.string.napping); // textview에 메시지를 넣음

        // 앞서 만든 SimpleAsyncTask 클래스를 통해 TextView를 넘겨주고 실행하여 SimpleAsyncTask에 구현한 랜덤하게 sleep해서 나오는 걸 보여주게 함
        // 그리고 현재 상태를 체크하기 위해서 progressBar도 넘겨줘서 생성자를 통해 보여줌
        new SimpleAsyncTask(mTextView,mProgressBar).execute();
    }

    // 만약 세로에서 가로모드로 보게 된다면 onDestroy가 실행되고 onCreate가 실행됨, 즉 현재 TextView 데이터가 사라지므로 이를 번들로 저장하기 위해서 쓰는 메소드
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // TextView 상태를 저장함
        outState.putString(TEXT_STATE,mTextView.getText().toString());

    }
}