package techtown.org.transition;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    public static final String ANIMATION = "Animation";
    // Explode 애니메이션 효과를 위한 key
    public static final String EXPLODE_ANIMATION = "Explode Animation";
    // Fade 애니메이션 효과를 위한 key
    public static final String FADE_TRANSITION = "Fade Transition";
    ImageView mYellowSquare;
    ImageView mGreenAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mYellowSquare = findViewById(R.id.image_square);
        mGreenAndroid = findViewById(R.id.image_android);

        // ANIMATION이라는 key를 Intent로 넘기고 위에서 정의한 효과의 value 값이 넘어오면 해당 애니메이션 실행시킴
        if(getIntent().hasExtra(ANIMATION)) {
            switch(getIntent().getStringExtra(ANIMATION)) {
                case EXPLODE_ANIMATION:
                    // Explode 효과 모든 View가 스크린 밖으로 나감
                    Explode explode = new Explode();
                    // 코드로 구현하기 위해 Transition 객체를 사용해서 함수 호출해야함
                    getWindow().setEnterTransition(explode);
                    break;
                case FADE_TRANSITION:
                    // Fade 효과 보였다가 안 보이는 효과
                    Fade fade = new Fade();
                    // 코드로 구현하기 위해 Transition 객체를 사용해서 함수 호출해야함
                    getWindow().setEnterTransition(fade);
                    break;
                default: break;
            }
        }
    }

    public void animationsAndTransitions(View view) {
        switch(view.getId()) {
            case R.id.image_circle:
                Intent intent = new Intent(this, MainActivity.class);
                // Explode 효과를 줌
                Explode explode = new Explode();
                explode.setDuration(400);
                getWindow().setExitTransition(explode);
                // Explode 효과이므로 이를 Intent로 넘김
                intent.putExtra(ANIMATION, EXPLODE_ANIMATION);
                // 공유 요소가 없기 때문에 activity 설정만 함
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
                // 애니메이션에 대한 정보를 가지고 그대로 넘김
                startActivity(intent, options.toBundle());
                break;

            case R.id.image_line:
                // 위에서 한 설명과 동일하고 Fade 효과인 것만 다름
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.putExtra(ANIMATION, FADE_TRANSITION);
                Fade fade = new Fade();
                fade.setDuration(400);
                getWindow().setExitTransition(fade);
                startActivity(intent2, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                break;

            case R.id.image_square:
                // drawable에 animation에 대한 내용을 정의하고 이를 load 해서 적용할 수 있음 rotation 하는 애니메이션 적용함
                Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
                mYellowSquare.startAnimation(rotateAnimation);
                break;

            case R.id.image_android:
                Intent intent3 = new Intent(MainActivity.this, SecondActivity.class);
                // 위에서 설명한 애니메이션과 다르게 이번엔 공유 소스가 있으므로 공유 요소를 정의하고 객체를 사용함
                // 이렇게 함으로써 다른 액티비티로 넘어가게 애니메이션 전환이 활용됨
                ActivityOptions options2 = ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create((View) mGreenAndroid, "swap_shared_transition_android_icon"),
                        Pair.create((View) mYellowSquare, "swap_shared_transition_square")
                );
                startActivity(intent3, options2.toBundle());
                break;

            default:
                break;
        }
    }
}