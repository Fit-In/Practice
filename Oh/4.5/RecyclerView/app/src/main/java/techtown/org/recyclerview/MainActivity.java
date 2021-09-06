package techtown.org.recyclerview;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    // 리사이클러뷰에 활용할 데이터
    private final LinkedList<String> mWordList = new LinkedList<>();

    // 사용해서 보여줄 RecyclerView와 앞서 데이터와 연결작업을 구현한 Adapter를 선언함
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // FloatingButton 누를 시 새로 LinkedList에 추가하고 adapte를 통해서 갱신을 알림
                int wordListSize = mWordList.size();
                // 새로운 word 추가하기
                mWordList.addLast("+ Word " + wordListSize);
                // adapter 갱신하고 바뀐 데이터를 넣기, 뒤에다가 추가하는 것인데 이는 size에 해당되므로 LinkedList 뒤에 추가한 부분을 넣으면 됨
                mRecyclerView.getAdapter().notifyItemInserted(wordListSize);
                // Scroll을 밑까지 할 수 있게함
                mRecyclerView.smoothScrollToPosition(wordListSize);
            }
        });

        // wordList로 만들기 위한 초기값 세팅
        for(int i = 0; i < 20; i++) {
            mWordList.addLast("Word " + i);
        }

        // 리사이클러뷰 xml 연결해서 불러옴
        mRecyclerView = findViewById(R.id.recyclerview);
        // 현재 여기서 선언하고 정의한 linkedlist와 Main에서 보여주고 활용할 것이므로 생성자를 통해 넘김
        mAdapter = new WordListAdapter(this, mWordList);
        // 그러면 이제 위에서 만든 LinkedList와 itemview를 통한 각종 설정과 세팅은 WordListAdapter에서 정의한대로 처리됨
        // 그런다음 이 Adapter를 RecyclerView에 연결함
        mRecyclerView.setAdapter(mAdapter);
        // 리사이클러뷰의 디폴트 레이아웃을 LinearLayout으로 줌
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}