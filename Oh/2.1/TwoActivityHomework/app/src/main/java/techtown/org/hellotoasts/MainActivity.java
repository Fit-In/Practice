package techtown.org.hellotoasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 현재 액티비티와 인텐트 처리를 위해서 정의함
    public static final String EXTRA_TEXT = "techtown.org.hellotoast.extra.TEXT";

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
        // onClick을 설정했으므로 현재 액티비티에서 count 값을 넘기기 위해서 intent로 처리해서 넘김
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_TEXT, mCount);
        startActivity(intent);
    }

    public void countUp(View view) {
        // XML 상 onClick 설정함, 버튼 누르면 카운트가 올라가고, 그 카운트 변수를 TextView에 나타내게끔 설정
        ++mCount;
        if (mShowCount != null) mShowCount.setText(Integer.toString(mCount));
    }
}