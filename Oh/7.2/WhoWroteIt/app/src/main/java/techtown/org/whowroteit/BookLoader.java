package techtown.org.whowroteit;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

/*
background에서 data를 load하거나 Activity의 background tasks에 대해서 연결지음, 만약 device가 rotate 되더라도
만약 굳이, UI를 update하는 것이 중요하지 않다면 AsyncTask만으로도 충분함
 */

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

    // context를 parameter로 받는 생성자, API Query를 받아서 할당함
    public BookLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    // 필수적으로 구현해야 하는 메소드
    @Nullable
    @Override
    public String loadInBackground() {
        // 통신을 통해 받은 query string을 리턴받음
        return NetworkUtils.getBookInfo(mQueryString);
    }

    // Loader를 시작할 때 불리는 system call임
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
    }
}
