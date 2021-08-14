package techtown.org.implicitintents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    // 입력값을 받기 위한 EditText 받아오기
    private EditText mWebsiteEditText;
    private EditText mLocationEditText;
    private EditText mShareTextEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebsiteEditText = findViewById(R.id.website_edittext);
        mLocationEditText = findViewById(R.id.location_edittext);
        mShareTextEditText = findViewById(R.id.share_edittext);
    }

    public void openWebsite(View view) {
        // 웹페이지 접속할 url 가져옴
        String url = mWebsiteEditText.getText().toString();

        // 인텐트 처리를 통해 웹페이지를 열기 위해 Uri 객체로 parse를 함
        Uri webpage = Uri.parse(url);
        // ACTION_VIEW, 주어진 데이터를 보기 위해서 활용
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // intent를 다룰 수 있는 액티비티를 찾고 데이터를 넘겨줌, 암시적 인텐트 실행함, 정상적으로 찾으면 웹페이지를 띄움
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    public void openLocation(View view) {
        // 찾고자 하는 위치에 대한 String을 받아옴
        String loc = mLocationEditText.getText().toString();

        // 해당 위치를 Uri로 parse하고 인텐트를 만듬
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW,addressUri);

        // intent로 다룰 수 있는 액티비티를 찾고 데이터를 넘겨줌, 암시적 인텐트 실행, 위치에 해당하는 곳을 띄움
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this!");
        }
    }

    public void shareText(View view) {
        // 공유할 String을 받아옴
        String txt = mShareTextEditText.getText().toString();
        // 공유할 텍스트의 타입을 정함
        String mimeType = "text/plain";

        ShareCompat.IntentBuilder
                .from(this) // this, 현재 Activity에서 Intent를 시작
                .setType(mimeType) // item이 공유될 MIME type을 정함
                .setChooserTitle(R.string.share_text_with) // 공유할 요소를 확인하는 시스템에서 맨 위에 제목으로 나오는 것
                .setText(txt) // 실제 공유할 text 저장
                .startChooser(); // 어떤 앱으로 공유할지 보여주고 Intent로 보내는 역할

    }
}