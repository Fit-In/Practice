package techtown.org.roomwordsample;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
Table Entity 나타내기 위한 클래스 즉, Table로 쓰여짐
PrimaryKey가 필요한 DB에서는 그래서 PrimaryKey 선언
ColumnInfo의 경우 테이블의 column을 의미함
DB 테이블로 만드므로
 */
@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {this.mWord = word;}

    public String getWord() {return this.mWord;}
}
