package techtown.org.whowroteit;

/*
internet 연결을 도움을 주는 helper 클래스, 어떤 클래스도 상속받지 않음
 */

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class NetworkUtils {
    // loggin을 위한 태그 설정
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    // 아래 부분은 BooksAPI를 사용하기 위한 URL과 찾기위한 function에 대한 상수값을 정의함, 이는 API 사용법을 보면 나와있음
    // Books API를 위한 기본 URL
    private static final String BOOK_BASE_URL =  "https://www.googleapis.com/books/v1/volumes?";
    // API 사용시 필요한 search String 매개변수
    private static final String QUERY_PARAM = "q";
    // 검색 결과의 제한을 위한 매개변수
    private static final String MAX_RESULTS = "maxResults";
    // print type의 filter를 위한 매개변수
    private static final String PRINT_TYPE = "printType";


    static String getBookInfo(String queryString) {
        //String 파라미터를 받고 API로부터 받은 JSON String 응답을 받을 것임
        HttpURLConnection urlConnection = null; // internet 연결을 위한 지역변수
        BufferedReader reader = null; // 들어오는 데이터를 읽기 위한 지역변수
        String bookJSONString = null; // 응답받은 데이터를 위한 지역변수

        try{
            // URI와 query문 처리, 모든 requests는 URI로 시작하기 때문에 이 부분에 대한 Builder를 혼합하여 query를 처리함
            // API 사용법대로 Uri 처리를 함

            // CodingChallenge -> appendQueryParameter("DOWNLOADBALE, "equb") 추가해서 처리함
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM,queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            // 처리한 URI를 URL객체로 변환함
            URL requestURL = new URL(builtURI.toString());
            // 이 API는 HttpURLConnection을 필요로 함(InputStream, BufferedReader, JSON 응답을 포함하는 StringBuffer를 가지는)
            // 만약 InputStream과 StringBuffer가 비어있다면 null을 리턴하고 query가 실패함
            // 이를 처리하기 위해 URLConnection을 만들고 요청을 보냄
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Connection 처리를 위해 InputStream, BufferedReader, StringBuilder를 셋업함
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            // input에 대해서 줄대로 읽기
            String line;
            while((line = reader.readLine()) != null) {
                builder.append(line);
                // JSON 데이터를 받아와서 builder에 붙임
                builder.append("\n");
            }
            // 만약 null을 받았다면 빈 값을 리턴
            if(builder.length() == 0) {
                return null;
            }
            // StringBuilder를 String으로 변환함
            bookJSONString = builder.toString();
        } catch(IOException e) {
            // request 보냈을 때의 문제를 처리
            e.printStackTrace();
        } finally {
            // JSON 데이터를 받고 난 뒤 인터넷 연결 닫음
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}
