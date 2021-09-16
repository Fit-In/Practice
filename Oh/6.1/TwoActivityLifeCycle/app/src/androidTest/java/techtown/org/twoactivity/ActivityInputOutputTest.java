package techtown.org.twoactivity;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ActivityInputOutputTest {

    // 사용하기 위해서 라이브러리 추가했어야 함, androidx.test.rules
    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("techtown.org.twoactivity", appContext.getPackageName());
    }

    // Send 버튼을 눌렀을 때 만약 Text가 없다면 SecondActivity가 나올지에 대한 테스트
    @Test
    public void activityLaunch() {
        // onView 메소드 Activity에서 테스트하려는 UI 구성 요소를 찾음(send 버튼)
        // 그런 다음 perform 즉, 메소드 호출 후 사용자 작업을 전달하여 해당 UI에서 실행할 특정 상호작용을 시뮬레이션 함, 여기선 click
        onView(withId(R.id.button_main)).perform(click());
        // 그런 다음 Send 버튼 눌렀을 때 Text 확인하기 위해 그 값을 불러옴
        // 그런 다음 matches에서 ViewAssertions 메서드를 통해서 예상 상태나 동작이 반영됐는지 확인, 제대로 Text가 노출되었는지
        onView(withId(R.id.text_header)).check(matches(isDisplayed()));
        // 이와 같이 Second 역시 확인함
        onView(withId(R.id.button_second)).perform(click());
        onView(withId(R.id.text_header_reply)).check(matches(isDisplayed()));
    }

    // EditText에 입력하고 send를 했을 때 정상적으로 send 처리가 됐는지에 대한 테스트
    @Test
    public void textInputOutput() {
        onView(withId(R.id.editText_main)).perform(typeText("This is a test."));
        onView(withId(R.id.button_main)).perform(click());
        onView(withId(R.id.text_message)).check(matches(withText("This is a failing test.")));
    }
}