package techtown.org.roomwordsample;

/*
Room은 반드시 RoomDatabase를 상속받아서 사용해야함
 */

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1, exportSchema = false) // Entity와 version, schema versions을 정의함
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();
    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized(WordRoomDatabase.class) {
                if(INSTANCE == null) {
                    // database를 인스턴스로써 생성함, db 테이블 생성
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class,"word_database")
                            .fallbackToDestructiveMigration() // Migration할 상황을 배제함
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute(); // 다 삭제하기 위해 AsyncTask 활용하여 Dao 적용
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;
        String[] words = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }


        @Override
        protected Void doInBackground(final Void... params) {
            // 데이터가 없을 때 초기 데이터 만듬
            if(mDao.getAnyWord().length < 1) {
                for(int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }


}
