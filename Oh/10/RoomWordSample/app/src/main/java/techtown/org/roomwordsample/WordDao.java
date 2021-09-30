package techtown.org.roomwordsample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao // SQL 쿼리와 관련 메소드를 호출함
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table") // 쿼리문을 통해서 word_table에서 지움
    void deleteAll();

    @Query("SELECT * from word_table ORDER BY word ASC") // 쿼리문을 통해서 word_table에 있는 내용을 불러오고 List에 Word 객체로 담음
    // LiveData 사용, 데이터가 변할 때 발 ㅗ응답하기 위해서
    LiveData<List<Word>> getAllWords(); // Words의 List를 모두 불러옴

    @Query("SELECT * from word_table LIMIT 1") // 특정 하나의 word를 리턴해옴
    Word[] getAnyWord();

    @Delete
    void deleteWord(Word word);
}
