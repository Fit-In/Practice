package techtown.org.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private TextView mShowCount;
    // 0으로 초기화하는 버튼 추가
    private Button mZeroButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        mZeroButton = (Button) findViewById(R.id.button_zero);

    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void countUp(View view) {
        ++mCount;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
            // null이 아닌 경우, 즉 0이 아닌 다른 값으로 시작된 상황에서 0을 눌러도 되는 상태 즉 초기화 가능 상태임을 나타내기 위해서 설정함
            mZeroButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }

        // 짝수이고 홀수인경우 배경색을 다르게 함, 여기서 findViewById를 쓰지 않고 onClick으로 이미 해당 뷰를 참조하고 있기 때문에 view를 직접 배경색 설정을 다르게 함
        if (mCount % 2 == 0){
           view.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));
        }

    }

    public void setZero(View view) {
        // 0으로 변수를 초기화 시키고 해당 텍스트를 0으로 바꿈
        mCount = 0;
        mShowCount.setText(Integer.toString(mCount));
        // 추가적으로 0으로 이미 초기화했기 때문에 이미 0인 상태를 나타내기 위해서 초기화 버튼의 배경을 바꿈
        mZeroButton.setBackgroundColor(getResources().getColor(R.color.black));
    }
}