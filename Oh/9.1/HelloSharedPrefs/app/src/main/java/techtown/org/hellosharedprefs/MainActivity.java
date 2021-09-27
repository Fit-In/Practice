package techtown.org.hellosharedprefs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 현재 count
    private int mCount = 0;
    // 현재 배경색
    private int mColor;
    // count, color를 보여주는 TextView
    private TextView mShowCountTextView;

    // 현재 count에 대한 Key
    private final String COUNT_KEY = "count";
    // 현재 color에 대한 Key
    private final String COLOR_KEY = "color";
    // 보내기 위한 key
    private final String COUNT_SAVE_KEY = "count_save";
    private String COUNT_EXTRA = "count_extra";

    // saveInstance가 아닌 SharedPreferences로 저장하기 위해서 아래의 멤버변수 선언
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "techtown.org.hellosharedprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 초기화
        mShowCountTextView = findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(this, R.color.default_background);

        // sharedPreferences를 가져옴
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

//        // 인스턴스 상태 저장 & 복구
//        if(savedInstanceState != null) {
//
//            mCount = savedInstanceState.getInt(COUNT_KEY);
//            if(mCount != 0) {
//                mShowCountTextView.setText(String.format("%s", mCount));
//            }
//
//            mColor = savedInstanceState.getInt(COLOR_KEY);
//            mShowCountTextView.setBackgroundColor(mColor);
//        }
        // saveInstance가 아닌 SharedPreferences를 사용함
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mShowCountTextView.setText(String.format("%s",mCount));
        mColor = mPreferences.getInt(COLOR_KEY, mColor);
        mShowCountTextView.setBackgroundColor(mColor);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 인스턴스 저장이 아닌 SharedPreferences를 활용하여 onPause 상태일 때 저장함
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        boolean countSave = mPreferences.getBoolean(COUNT_SAVE_KEY, true);
        // countSave 값에 따라서 설정을 함
        if(countSave) {
            preferencesEditor.putInt(COUNT_KEY, mCount);
        } else {
            preferencesEditor.putInt(COUNT_KEY, 0);
        }
        preferencesEditor.apply();
    }

    public void changeBackground(View view) {
        // background color 버튼을 누르면 해당 color을 가져옴
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }

    public void countUp(View view) {
        // 숫자를 증가시킴
        mCount++;
        mShowCountTextView.setText(String.format("%s",mCount));
    }

//    // 인스턴스 상태 저장
//
//
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putInt(COUNT_KEY, mCount);
//        outState.putInt(COLOR_KEY, mColor);
//    }

    public void reset(View view) {
        mCount = 0;
        mShowCountTextView.setText(String.format("%s",mCount));

        mColor = ContextCompat.getColor(this, R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);

//        // sharedPreferences도 리셋함
//        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
//        preferencesEditor.clear();
//        preferencesEditor.apply();
    }

    public void onSettingsClick(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}