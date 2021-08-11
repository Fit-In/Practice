package techtown.org.twoactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // class를 정의한 키 값을 상수로 정의함
    public static final String EXTRA_MESSAGE = "techtown.org.twoactivity.extra.MESSAGE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void launchSecondActivity(View view) {
        // secondActivity로 넘어가게 설정함
        Intent intent = new Intent(this, SecondActivity.class);

        // 3개의 버튼에 같은 onClick 메소드를 선언했으므로 선택한 메소드에 따라 인텐트로 넘기도록 설정함
        // 넘길 String은 String.xml에 미리 선언함
        switch(view.getId()){
            // 각각 버튼을 눌렀을 때 각각 view에 onClick 설정을 했으므로, 누른 onClick에 따라서 id를 찾아서 하면 됨
            case R.id.button_one:
                // intent로 넘김, 근데 여기서 String으로 넘기기 위해서 getString으로 넘겨줘야함, 그렇지 않으면 String으로 넘어가지 않고 해당 id만 참조하므로 값이 넘어가지 않음
                intent.putExtra(EXTRA_MESSAGE,getString(R.string.text_one));
                startActivity(intent);
                break;
            case R.id.button_two:
                intent.putExtra(EXTRA_MESSAGE,getString(R.string.text_two));
                startActivity(intent);
                break;
            case R.id.button_three:
                intent.putExtra(EXTRA_MESSAGE,getString(R.string.text_three));
                startActivity(intent);
                break;
            default:
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                break;
        }

    }


}