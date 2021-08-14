package techtown.org.implicitintentsreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 먼저 인텐트를 받고 거기서 해당하는 Uri 데이터를 받음
        // 이전에 만든 ImplicitIntent 앱에서 Open Webpage를 누르면 이 Reciver 앱에도 인텐트 데이터를 받아서 띄울 수 있음
        Intent intent = getIntent();
        Uri uri = intent.getData();

        // uri가 null이 아니라면 TextView에서 해당 uri String을 보여주게 세팅함
        if(uri != null) {
            String uri_string = getString(R.string.uri_label) + uri.toString();
            TextView textView = findViewById(R.id.text_uri_message);
            textView.setText(uri_string);
        }
    }
}