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
            mZeroButton.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }
        if (mCount % 2 == 0){
           view.setBackgroundColor(getResources().getColor(R.color.design_default_color_primary_dark));
        } else {
            view.setBackgroundColor(getResources().getColor(R.color.design_default_color_secondary));
        }

    }

    public void setZero(View view) {
        mCount = 0;
        mShowCount.setText(Integer.toString(mCount));
        mZeroButton.setBackgroundColor(getResources().getColor(R.color.black));
    }
}