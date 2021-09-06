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
import java.util.Objects;

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
        if (id == R.id.action_reset) {
            // reset 버튼 누르면 데이터를 다 날리고 다시 세팅
            mWordList.clear();

            // wordList로 만들기 위한 초기값 세팅
            for(int i = 0; i < 20; i++) {
                mWordList.addLast("Word " + i);
            }

//            // 그리고 리셋으로 초기화하고 다시 해당 LinkedList 넘겨서 보여줌 -> CodingChallenge1(클래식한 방식)
//            mAdapter = new WordListAdapter(this, mWordList);
//            mRecyclerView.setAdapter(mAdapter);
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            // 위에서 한 번 만든뒤 어댑터에 리스너를 다시 연결하는 과정이 중복이고 데이터가 많아지면 오류가 날 수 있음
            // 이를 코드 하나로 간결하게 처리 가능함
            // 만약 리셋을 통해서 리사이클러뷰의 데이터를 다 날려버리고 정상적으로 받아왔다면 null이 아님, 이를 null 체크를 requireNonNull로 한 뒤
            // notifyDataSetChanged를 함, 위처럼 중복코드를 사용할 필요가 없는게 어차피 WordListAdapter는 mWordList와 연결되었으므로
            // 만약 reset 버튼을 통해서 데이터가 통째로 날라가고 새로 초기값이 세팅이 됐다면 이 메소드 하나만을 가지고 데이터가 변한것이므로 View를 갱신할 수 있음
            // 어댑터에게 리스트가 변할 경우를 알려준 것이므로 -> CodingChallenge2(Advanced 버전)
            Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}