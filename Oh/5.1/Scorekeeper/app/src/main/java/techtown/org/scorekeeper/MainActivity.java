package techtown.org.scorekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int mScore1;
    private int mScore2;

    private TextView mScoreText1;
    private TextView mScoreText2;

    static final String STATE_SCORE_1 = "Team 1 Score";
    static final String STATE_SCORE_2 = "Team 2 Score";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // score를 화면을 돌려도 저장하기 위해서 사용, 번들로 저장
        outState.putInt(STATE_SCORE_1, mScore1);
        outState.putInt(STATE_SCORE_2, mScore2);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScoreText1 = (TextView)findViewById(R.id.score_1);
        mScoreText2 = (TextView)findViewById(R.id.score_2);

        if(savedInstanceState != null) {
            // 이전에 작업한 데이터에 대해서 불러와서 씀
            mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
            mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

            mScoreText1.setText(String.valueOf(mScore1));
            mScoreText2.setText(String.valueOf(mScore2));
        }
    }

    // 감소버튼 누르면 숫자가 감소하는 메소드
    public void decreaseScore(View view) {
        // 감소버튼이 2개이므로 어떤 View를 눌렀는지 먼저 확인함
        int viewID = view.getId();
        // 누른 감소버튼에 맞춰서 해당 View에 맞는 score 감소시킴
        switch (viewID) {
            case R.id.decreaseTeam1:
                mScore1--;
                mScoreText1.setText(String.valueOf(mScore1));
                break;
            case R.id.decreaseTeam2:
                mScore2--;
                mScoreText2.setText(String.valueOf(mScore2));
                break;

        }
    }

    // 증가버튼 누르면 숫자가 증가하는 메소드
    public void increaseScore(View view) {
        // 증가버튼이 2개이므로 어떤 View를 눌렀는지 먼저 확인함
        int viewID = view.getId();
        // 누른 증가버튼에 맞춰서 해당 View에 맞는 score 증가시킴
        switch(viewID) {
            case R.id.increaseTeam1:
                mScore1++;
                mScoreText1.setText(String.valueOf(mScore1));
                break;
            case R.id.increaseTeam2:
                mScore2++;
                mScoreText2.setText(String.valueOf(mScore2));
                break;
        }
    }

    // 다크모드로 바꾸기 위해서 메뉴 만듬
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        int nightMode = AppCompatDelegate.getDefaultNightMode();
        if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
        } else {
            menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
        }
        return true;
    }

    // 메뉴 클릭시 다크모드로 바꾸게 처리함
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.night_mode) {
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        }
        // 테마를 바꾸고 액티비티를 다시 만듬
        recreate();

        return true;
    }
}