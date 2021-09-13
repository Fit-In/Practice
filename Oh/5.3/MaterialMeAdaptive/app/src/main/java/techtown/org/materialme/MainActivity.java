package techtown.org.materialme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<Sport> mSportsData;
    private SportsAdapter mAdapter;
    // ImageID를 쉽게 저장할 수 있게 사용한 자료구조 TypedArray
    private TypedArray sportsImageResources;
    private static final String BUNDLE_KEY = "Sports_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // land, portrait 버전을 다르게 하기 위해서 불러옴
        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        // 리사이클러뷰 초기화하기
        mRecyclerView = findViewById(R.id.recyclerView);

        // LayoutManager 설정(Grid로 설정), 자동으로 landscape되면 설정 바뀜
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, gridColumnCount));

        // 데이터 담을 ArrayList 초기화
        mSportsData = new ArrayList<>();

        // 어댑터 초기화하고 리사이클러뷰에 세팅하기, 생성자 통해 데이터와 리사이클러뷰가 있는 context 넘겨줌
        mAdapter = new SportsAdapter(this, mSportsData);
        mRecyclerView.setAdapter(mAdapter);

        // 1개 이상일 경우 스와이프 못하게 처리함
        int swipeDirs;
        if(gridColumnCount > 1) {
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }

        // 드래그앤 드랍, 스와이프 등의 상황을 처리함
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN, swipeDirs) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();
                Collections.swap(mSportsData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mSportsData.remove(viewHolder.getAdapterPosition());
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
    }

    private void initializaData() {
        // string으로 미리 저장해둔 text에 대해서 가지고 옴
        String[] sportsList = getResources().getStringArray(R.array.sports_titles);
        String[] sportsInfo = getResources().getStringArray(R.array.sports_info);
        String[] sportsDetails = getResources().getStringArray(R.array.sports_details);
        // TypedArray로 미리 선언했으므로 이를 가져오기만 하면 됨
        sportsImageResources = getResources().obtainTypedArray(R.array.sports_images);

        // 초기화 작업이므로 기존의 데이터를 없앰
        mSportsData.clear();

        // Sport 객체의 ArrayList를 만듬
        for(int i = 0; i < sportsList.length; i++) {
            mSportsData.add(new Sport(sportsList[i], sportsInfo[i], sportsImageResources.getResourceId(i,0), sportsDetails[i]));
        }
        sportsImageResources.recycle();
        // 어댑터의 변화도 확인함
        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializaData();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // 번들을 통해서 title, description, image, info를 저장해둠
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(BUNDLE_KEY, mSportsData);
    }
}