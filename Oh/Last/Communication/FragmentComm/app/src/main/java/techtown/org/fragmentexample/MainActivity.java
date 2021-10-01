package techtown.org.fragmentexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SimpleFragment.OnFragmentInteractionListener{ // 라디오 버튼 선택을 알기 위해 인터페이스 구현

    private Button mButton;
    private boolean isFragmentDisplayed = false;

    // 프래그먼트 상태를 저장하기 위한 키값
    static final String STATE_FRAGMENT = "state_of_fragment";

    // 라디오 버튼 선택을 알기 위한 디폴트 값
    private int mRadioButtonChoice = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = findViewById(R.id.open_button);

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(!isFragmentDisplayed) {
                    displayFragment();
                } else {
                    closeFragment();
                }
            }
        });

        if(savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT);

            if(isFragmentDisplayed) {
                mButton.setText(R.string.close);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed); // 프래그먼트 상태 저장
        super.onSaveInstanceState(outState);
    }

    public void displayFragment() {
        SimpleFragment simpleFragment = SimpleFragment.newInstance(mRadioButtonChoice); // 프래그먼트 인스턴스 생성
        // 프래그먼트 매니저를 불러와 transaction 시작
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // 위에서 인스턴스로 생성한 요소를 메인에 있는 FrameLayout에 추가해서 그림
        fragmentTransaction.add(R.id.fragment_container,simpleFragment).addToBackStack(null).commit();
        // 버튼 업데이트
        mButton.setText(R.string.close);
        // boolean flag 변경
        isFragmentDisplayed = true;
    }

    // 위와 비슷하게 활용하여 프래그먼트 닫는 로직
    public void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        // fragment가 현재 보여지고 있는지 확인함
        SimpleFragment simpleFragment = (SimpleFragment) fragmentManager.findFragmentById(R.id.fragment_container);

        if(simpleFragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        mButton.setText(R.string.open);
        isFragmentDisplayed = false;
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
        Toast.makeText(this, "Choice is " + Integer.toString(choice), Toast.LENGTH_SHORT).show();
    }
}