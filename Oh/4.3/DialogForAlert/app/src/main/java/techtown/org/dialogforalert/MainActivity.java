package techtown.org.dialogforalert;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowAlert(View view) {
        // Alert 다이얼로그를 띄우기 위해서 Builder를 통해서 만듬
        AlertDialog.Builder myAlertBuilder = new AlertDialog.Builder(MainActivity.this);
        // 타이틀과 메시지 설정
        myAlertBuilder.setTitle(R.string.alert_title);
        myAlertBuilder.setMessage(R.string.alert_message);
        // Alert 다이얼로그에서 버튼을 추가함, 흔히 아는 확인, 취소 버튼
        myAlertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            // 확인 버튼 누를 시 이벤트 직접 처리하기 위해서 인터페이스 구현
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Pressed OK",Toast.LENGTH_SHORT).show();
            }
        });
        myAlertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            // 취소 버튼 누를 시 이벤트 직접 처리하기 위해서 인터페이스 구현
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Pressed Cancel",Toast.LENGTH_SHORT).show();
            }
        });
        // 마지막에 AlertDialog를 만듬
        myAlertBuilder.show();
    }
}