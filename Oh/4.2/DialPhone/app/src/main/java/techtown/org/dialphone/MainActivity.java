package techtown.org.dialphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText_main);
        if(editText != null)
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            // editText를 입력시 나오는 상호작용
                @Override
                public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                    boolean handled = false;
                    if(actionId == EditorInfo.IME_ACTION_SEND){
                        dialNumber();
                        handled = true;
                    }
                    return handled;
                }
            });
    }

    private void dialNumber() {
        EditText editText = findViewById(R.id.editText_main);
        String phoneNum = null;

        // edittext로 숫자 다이얼을 입력받음
        if (editText != null) phoneNum = "tel:" + editText.getText().toString();

        Log.d(TAG, "dialNumber: " + phoneNum);

        // 입력받은 다이얼에 대해서 인텐트로 데이터를 넘김
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phoneNum));
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }
}