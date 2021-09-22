package techtown.org.websourcecode;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
network 연결을 돕는 Helper 클래스, HTTP, HTTPS를 구분하고 해당하는 query문을 가져와서 앞서 작업한대로 StringBuilder, InputStream 작업을 함
*/
public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    static String getSourceCode(Context context,String queryString, String transferProtocol) {
        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;
        String htmlSourceCode = null;
        String[] protocol = context.getResources().getStringArray(R.array.http_array);
        try{
            Uri builder; // HTTP, HTTPS가 다르기 때문에 builder를 지역변수로 선언하고 사용함
            if (transferProtocol.equals(protocol[0])) {
                // 만약 http라면
                builder = Uri.parse(queryString).buildUpon()
                        .scheme(HTTP)
                        .build();
            } else {
                // 만약 Https라면
                builder = Uri.parse(queryString).buildUpon()
                        .scheme(HTTPS)
                        .build();
            }
            URL requestURL = new URL(builder.toString()); // Uri를 URL로 변환함
            // httpURLConnection이 자동으로 http, htpps 베이스의 URI scheme 처리함
            // 네트워크를 통해서 URL을 연결하고 GET으로 받아옴
            httpURLConnection = (HttpURLConnection) requestURL.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            // 받아온 데이터를 읽어서 처리함
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            if(stringBuilder.length() == 0) {
                return null;
            }
            htmlSourceCode = stringBuilder.toString();
        } catch(IOException e) {
            // 만약 연결이 되어 있지 않다면
            e.printStackTrace();
        } finally {
            // 연결을 완료하고 종료함
            if(httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, htmlSourceCode);
        return htmlSourceCode;

    }
}
