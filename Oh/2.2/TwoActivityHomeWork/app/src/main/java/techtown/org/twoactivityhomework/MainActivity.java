package techtown.org.twoactivityhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Count를 체크하기 위한 값
    private int mCount = 0;
    private TextView mShowCount;
    // 입력받는 EditText
    private EditText mWriteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.count_TextView);
        mWriteText = (EditText) findViewById(R.id.write_EditText);

        // 보존한 값을 EditText에 그대로 남김
        if(savedInstanceState != null) {
            String writeText = savedInstanceState.getString("write_text");
            mWriteText.setText(writeText);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // 핸드폰 화면이 변경되었을 때 값을 보존하기 위해서 저장함
        if(mWriteText != null) {
            outState.putString("write_text", mWriteText.getText().toString());
        }
    }

    public void CountUp(View view) {
        // COUNT 버튼을 누르면 COUNT가 계속해서 올라감
        ++mCount;
        if(mShowCount != null) mShowCount.setText(Integer.toString(mCount));
    }
}