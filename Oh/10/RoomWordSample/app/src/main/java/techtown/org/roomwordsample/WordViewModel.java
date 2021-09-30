package techtown.org.roomwordsample;

/*
UI 데이터가 바뀌게 된다면 ViewModel이 데이터 변화를 바로 적용하기 때문에
Activity Fragment와 다르게 UI를 그리는데 책임이 있지만 ViewModel은 UI에 필요한 데이터만을 처리하기 때문에
더 효과적일 때가 있음
 */

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {return mAllWords;}

    public void insert(Word word) { mRepository.insert(word); }

    public void deleteAll() {mRepository.deleteAll();}

    public void deleteWord(Word word) {mRepository.deleteWord(word);}
}
