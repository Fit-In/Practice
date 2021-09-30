package techtown.org.roomwordsample;

/*
다양한 데이터 소스에 접근할 수 있는 클래스임, Repository 자체를 Network에서 데이터 업데이트 혹은 로컬 DB의 캐시 업데이트에서도 활용을 함
 */

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WordRepository {

    // Dao를 통해서 데이터를 가져오고 LiveData에 업데이트를 함
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert (Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll() {
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    public void deleteWord(Word word) {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }


    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }


    }

    private static class deleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}
