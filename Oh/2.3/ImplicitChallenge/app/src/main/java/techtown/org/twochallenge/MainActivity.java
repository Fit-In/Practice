package techtown.org.twochallenge;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int ITEM_REQUEST = 1;

    // 쇼핑 리스트에 담는 것이므로 TextView에 각각 지정해주기 위해서 TextView를 Array로 선언하고 담음
    private final TextView[] item = new TextView[10];
    // 각각 Intent로 그 텍스트 값을 넘겨 받고 저장한 뒤 TextView에 보여줄 것이므로 ArrayList로 선언함
    private ArrayList<String> itemList = new ArrayList<>(10);

    private EditText mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLocation = findViewById(R.id.location_edittext);

        // 각각 TextView 연결
        item[0] = findViewById(R.id.oneItemTextView);
        item[1] = findViewById(R.id.twoItemTextView);
        item[2] = findViewById(R.id.threeItemTextView);
        item[3] = findViewById(R.id.fourItemTextView);
        item[4] = findViewById(R.id.fiveItemTextView);
        item[5] = findViewById(R.id.sixItemTextView);
        item[6] = findViewById(R.id.sevenItemTextView);
        item[7] = findViewById(R.id.eightItemTextView);
        item[8] = findViewById(R.id.nineItemTextView);
        item[9] = findViewById(R.id.tenItemTextView);

        // 핸드폰을 돌릴때 Activity가 잠깐 사라지는데 그때 데이터가 사라지는것을 방지하기 위해서 설정
        if(savedInstanceState != null) {
            itemList = savedInstanceState.getStringArrayList("ItemList");
            // 저장해둔 상태값에 대해서 다시 보여주기 위해서 다시 설정함
            if(itemList != null && itemList.size() > 0) {
                for(int i = 0; i < itemList.size(); i++){
                    if(itemList.size() > 10) {
                        Toast.makeText(MainActivity.this, "더 이상 추가할 수 없습니다", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    item[i].setText(itemList.get(i));
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        // 데이터가 날아가는 것을 방지하기 위해 그 상태값을 저장해둠
        if(itemList != null) {
            outState.putStringArrayList("ItemList",itemList);
        }
    }

    public void takeItemActivity(View view) {
        // 다음 인텐트로 넘어감, 여기서 선택한 품목을 돌려 받으므로 StartActivityForResult로 받음
        Intent intent = new Intent(this, SecondActivity.class);
        startActivityForResult(intent,ITEM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ITEM_REQUEST) {
            if(resultCode == RESULT_OK) {
                // 버튼의 텍스트를 넘겨받는 값에서 해당 String을 정상적으로 받았다면
                String reply = data.getStringExtra(SecondActivity.ITEM_REPLY);
                // itemList ArrayList에다 String 저장함
                itemList.add(reply);
                for(int i = 0; i < itemList.size(); i++) {
                    // 크기만큼 반복해서 TextView에 각각 추가함, 이렇게 반복하면 버튼을 계속 누를 때마다 결과값으로 TextView에 쌓여서 저장됨
                    if(itemList.size() > 10) {
                        Toast.makeText(MainActivity.this, "더 이상 추가할 수 없습니다", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    // 받은 itemList를 item 뷰에다가 설정함
                    item[i].setText(itemList.get(i));
                }
            }
        }
    }

    public void openLocation(View view) {
        String loc = mLocation.getText().toString();

        Uri addressUri = Uri.parse("geo:0,0?q="+loc);
        Intent intent = new Intent(Intent.ACTION_VIEW,addressUri);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntent", "잘못된 설정입니다!");
        }
    }
}