package techtown.org.twochallenge;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    public static final String ITEM_REPLY = "techtwon.org.twochallenge.extra.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }

    public void returnItem(View view) {
        // 다 동일한 메소드이므로 reply를 받는데 Button으로 누른 view를 받아서 그 텍스트를 넘김
        String reply = ((Button)view).getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(ITEM_REPLY,reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}