package techtown.org.websourcecode;

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
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

// AsyncTask와 연결하기 위한 Laoder와 spinner adapter를 연결해서 아이템 선택과 관련한 인터페이스 구현함
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, AdapterView.OnItemSelectedListener{

    private String mSpinnerValue;
    private EditText mURLEditText;
    private TextView mSourceCodeTextView;
    private static final String QUERY = "queryString";
    private static final String PROTOCOL = "transferProtocol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mURLEditText = findViewById(R.id.url_EditText);
        mSourceCodeTextView = findViewById(R.id.page_source_code);

        // Spinner adapter 설정, 미리 정의한 http, https 아이템을 dropdown 형태의 스피너로 설정함
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.http_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = findViewById(R.id.http_spinner);
        if(spinner != null) {
            spinner.setOnItemSelectedListener(this);
            spinner.setAdapter(adapter);
        }
    }

    // loader 관련 메소드 구현
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle bundle) {
        // AsyncTaskLoader와 연결하기 위해서 bundle로 받고 넘김
        String queryString = "";
        String transferProtocol = "";
        if (bundle != null) {
            queryString = bundle.getString(QUERY);
            transferProtocol = bundle.getString(PROTOCOL);
        }
        return new SourceCodeLoader(this, queryString, transferProtocol);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // 정상적으로 연결 되었담녀 결과값을 띄움
            mSourceCodeTextView.setText(data);
        } catch (Exception e) {
            // 만약 제대로 응답이 안왔을 경우
            e.printStackTrace();
            mSourceCodeTextView.setText(R.string.no_response);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    // spinner adapter에서 아이템 선택 관련 메소드 구현
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSpinnerValue = parent.getItemAtPosition(position).toString(); // spinner의 값을 선택시 그 값을 할당함
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        String[] values = getResources().getStringArray(R.array.http_array);
        mSpinnerValue = values[0];
    }

    public void getSourceCode(View view) {
        // 키보드 숨김(입력 후)
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(inputMethodManager!=null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }

        String queryString = mURLEditText.getText().toString(); // 입력한 URL을 query로 보내기 위해 변환함

        // 네트워크 연결을 체크하고 정상적으로 연결되었다면 bundle로 데이터를 넘겨서 처리함(AsyncTaskLoader 사용법)
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }

        if(networkInfo != null && networkInfo.isConnected() && (queryString.length() != 0)) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString(QUERY, queryString);
            queryBundle.putString(PROTOCOL, mSpinnerValue);
            getSupportLoaderManager().restartLoader(0,queryBundle,this);
            mSourceCodeTextView.setText(R.string.loading);
        } else {
            if (queryString.length() == 0){
                Toast.makeText(this, R.string.no_url, Toast.LENGTH_LONG).show();
            }
            else if(!URLUtil.isValidUrl(queryString)){
                Toast.makeText(this, R.string.invalid_url, Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, R.string.no_connection, Toast.LENGTH_LONG).show();
            }
        }
    }


}