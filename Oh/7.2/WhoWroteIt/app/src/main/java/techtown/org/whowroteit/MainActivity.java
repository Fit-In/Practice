package techtown.org.whowroteit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> { // AsyncTaskLoader와 Activity를 연결하기 위해 interface 구현함

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = (EditText)findViewById(R.id.bookInput);
        mTitleText = (TextView)findViewById(R.id.titleText);
        mAuthorText = (TextView)findViewById(R.id.authorText);

        // loader와 연결하기 위한 작업
        if(getSupportLoaderManager().getLoader(0)!=null) {
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    public void searchBooks(View view) {
        // EditText에 입력된 값을 바탕으로 search를 함
        String queryString = mBookInput.getText().toString();

        // EditText 이후 키보드가 사라지지 않으므로 사라지게끔 설정함
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if(inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        // 만약 네트워크 연결이 안되어 있을 경우를 처리하기 위한 로직도 설계해야함, 이를 확인함
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        // 정상적으로 연결이 됐다면 아래와 같이 처리
        if(networkInfo != null && networkInfo.isConnected() && queryString.length() != 0) {
            // AsyncTask 사용시
//            // FetchBook을 통해서 input으로 받은 값을 기준으로 title, author를 받아옴, FetchBook에는 Network클래스를 통해서 API와 통신해서 받아옴
//            new FetchBook(mTitleText, mAuthorText).execute(queryString);
//            // execute 호출 후 loading 하게끔 나타냄
            // Loader 사용시, bundle 객체로 보냄
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            // id -> activity에서 loader를 부를때 씀, bundle 데이터를 보내고, callback -> loadercallback을 어디다 적용할지 씀, 현재 액티비티에 쓰므로 this
            getSupportLoaderManager().restartLoader(0,queryBundle,this);
            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        } else {
            // 연결이 원할이 되지 않았음을 나타냄
            if(queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }
    }

    // interface 구현을 위한 메소드
    @NonNull
    @Override // loader를 인스턴스화 할 때 호출됨
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        // 위에서 Loader 사용시 bundle로 넘겼으므로 bundle을 받아서 처리함
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }
        // 받은 bundle의 query를 처리함
        return new BookLoader(this, queryString);
    }

    @Override // loader의 일이 끝나면 호출됨
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        // doInBackground로 통신을 연결하고 로딩이 끝나고 해당 데이터에 대해서 JSON객체를 JSONArray로 변환함
        // Loader 사용시 통신 끝나고 JSON 객체 변환 작업
        try {
            // 응답받은 데이터를 변환함
            JSONObject jsonObject = new JSONObject(data);
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
                mTitleText.setText(title);
                mAuthorText.setText(authors);
            } else {
                // 만약 응답데이터가 없다면 빈 값, no results 표시함
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }
        } catch(Exception e) {
            // JSON string을 제대로 받지 못하면 실패 결과를 보여줌
            // 만약 응답데이터가 없다면 빈 값, no results 표시함
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
            e.printStackTrace();
        }
    }

    @Override // 남은 resources를 초기화시킴
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}