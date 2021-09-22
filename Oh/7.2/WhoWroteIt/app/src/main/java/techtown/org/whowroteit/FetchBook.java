package techtown.org.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

/*
BooksAPI와 인터넷 연결을 처리할 AsyncTask 클래스, 네트워크 연결을 핸들링 함
 */
// String -> query가 String이므로, Void -> progress를 나타낼 필요 없음, String -> JSON이 String 형식이므로
public class FetchBook extends AsyncTask<String, Void, String> {

    // 메모리 누수 방지를 위한 WeakReference사용
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    // TextView를 업데이트 하기 위한 생성자 생성
    public FetchBook(TextView titleText, TextView authorText) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
    }

    @Override
    protected String doInBackground(String... strings) {
        // API 사용하기 위해서 book 데이터를 가져옴
        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // doInBackground로 통신을 연결하고 로딩이 끝나고 해당 데이터에 대해서 JSON객체를 JSONArray로 변환함
        try {
            // 응답받은 데이터를 변환함
            JSONObject jsonObject = new JSONObject(s);
            // API 문서 상 items에 title과 authors의 정보가 있음
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // 응답받은 JSON 객체를 파싱하기 전에 변수를 초기화함
            int i = 0;
            String title = null;
            String authors = null;

            // 반복문을 통해서 책 정보에 대해서 가져옴, 여기서 items 중 volumeInfo에 title과 authors의 정보가 있음
            while(i < itemsArray.length() && (authors == null && title == null)) {
                // 현재 item 정보를 가져옴
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // 현재 item에 있는 author와 title 정보를 가져옴
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++; // 다음 item으로 넘어감
            }

            if(title != null && authors != null) {
                // 만약 응답데이터가 있다면 UI를 업데이트 함
                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);
            } else {
                // 만약 응답데이터가 없다면 빈 값, no results 표시함
                mTitleText.get().setText(R.string.no_results);
                mAuthorText.get().setText("");
            }
        } catch(Exception e) {
            // JSON string을 제대로 받지 못하면 실패 결과를 보여줌
            // 만약 응답데이터가 없다면 빈 값, no results 표시함
            mTitleText.get().setText(R.string.no_results);
            mAuthorText.get().setText("");
            e.printStackTrace();
        }
    }
}
