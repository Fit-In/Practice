package techtown.org.recyclerview;

/*
리스트에 있는 View와 data를 연결하기 위해서 Adapter를 사용함
앞서 메인에서 LinkedList로 구현한 word list와 View items을 연결할 것임
data와 View를 연결하기 위해서 adapter는 어떤 View인지 알아야 하는데 이때 ViewHolder를 사용해서 리사이클러뷰에 있는 View와 그 위치에 대해서 알게됨
그래서 아래와 같이 상속을 받고 메소드를 구현하여 활용함
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder>{
    // 데이터를 hold하기 위해 LinkedList를 선언함
    private final LinkedList<String> mWordList;

    // word list라는 데이터를 가지고 xml로 만든 layout으로 나타내고 연결해서 초기화 하기 위해서 LayoutInflator를 사용함
    private LayoutInflater mInflater;

    public WordListAdapter(Context context,LinkedList<String> wordList) {
        // wordlist 데이터를 초기화함 LayoutInflator는 mInflater를 초기화하고
        // mWordList는 정의한 LinkedList에 데이터를 초기화함
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }


    // Adapter에 대해서 상속 받아 메소드를 구현하고 그 다음 WordViewHolder에 대한 정보가 없기 때문에 클래스를 정의하고 생성자를 만들어서 정의함
    // 이때 ViewHolder를 상속받음
    // 여기서 item에 대해서 사용자의 인풋을 받고 클릭을 하는등의 상호작용을 하기 위해서 클릭리스너 인터페이스를 구현함
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // 데이터를 연결해서 TextView로 word list에 있는 데이터를 보여줄 것이므로 선언함
        // 현재 adapter를 통해서 view와 data를 묶을 것이므로 먼저 정의함
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            // 생성자를 통해서 데이터를 보여줄 View와 이를 연결할 adapter를 초기화함
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
            // 클릭이벤트를 처리하기 위해서 생성자를 통해서 itemView 클릭시 처리 위해서 추가함
            itemView.setOnClickListener(this);
        }

        // 리사이클러뷰에 있는 아이템에 대한 클릭 상호작용을 위한 메소드 오버라이드(구현)
        @Override
        public void onClick(View view) {
            // 아이템이 클릭된 부분에 대한 위치를 가져옴
            int mPosition = getLayoutPosition();
            // 클릭한 부분에 해당하는 item을 LinkedList에서 가져옴
            String element = mWordList.get(mPosition);
            // 클릭했을 때 해당 부분에 대해서 word를 바꿈
            mWordList.set(mPosition, "Clicked! " + element);
            // adapter에게 데이터가 바뀌었음을 알림 + 그리고 리사이클러뷰에 데이터를 업데이트함
            mAdapter.notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 위에서 생성자를 통해서 초기화한 부분에 대해서 실제 연결작업을 함
        // itemView에 경우 앞서 정의한 xml파일에 해당함
        View mItemView = mInflater.inflate(R.layout.wordlist_item, parent, false);

        // 앞서 생성자로 정의한 부분에 맞게 연결하기 위해서, 넘겨받은 xml과 어댑터를 받음
        return new WordViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        // 앞서 정의한 TextView에 현재 LinkedList에 있는 String을 불러오고 연결시킴
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        // wordlist의 데이터 사이즈를 리턴함
        return mWordList.size();
    }
}
