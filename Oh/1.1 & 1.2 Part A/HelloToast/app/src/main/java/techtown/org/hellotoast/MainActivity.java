package techtown.org.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // count를 체크하는 변수와 그걸 나타내는 TextView
    private int mCount = 0;
    private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
    }

    public void showToast(View view) {
        // XML 상 onClick 설정함, 버튼 누르면 토스트 메시지가 뜸
        Toast toast = Toast.makeText(this, R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        // XML 상 onClick 설정함, 버튼 누르면 카운트가 올라가고, 그 카운트 변수를 TextView에 나타내게끔 설정
        ++mCount;
        if (mShowCount != null) mShowCount.setText(Integer.toString(mCount));
    }
}