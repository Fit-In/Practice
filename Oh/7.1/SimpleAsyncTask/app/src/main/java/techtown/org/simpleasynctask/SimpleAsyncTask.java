package techtown.org.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

/*
AsyncTask 클래스, 주로 background task를 처리함, 데이터베이스 쿼리, 인터넷 연결, 다음 항목 실행등
AsyncTask에서 parameter로 넘겨 받고 subclass이므로 메소드를 구현해야하며 여기서 구현한 doInBackground는 task를 보내는 역할을 함
 */

public class SimpleAsyncTask extends AsyncTask<Void, Integer, String> {
    // AsyncTask로 background 작업시 메모리 누수를 막기 위해 사용한 WeakReference
    private WeakReference<TextView> mTextView;
    private WeakReference<TextView> mResultTextView;

    // AsyncTask 생성자, WeakReference를 만드는 TextView, AsyncTask를 통해 onPostExecute이후 view를 업데이트 해주기 위한 생성자
    SimpleAsyncTask(TextView tv, TextView result) {
        mTextView = new WeakReference<>(tv);
        mResultTextView = new WeakReference<>(result);
    }

    @Override
    protected String doInBackground(Void... voids) {
        // 숫자 랜덤하게 생성
        Random r = new Random();
        int n = r.nextInt(11);

        int s = n * 200;
        publishProgress(s); // 현재 상태를 보여주기 위해서 사용
        // thread를 sleep 상태로 두기 위해 try / catch로 구분
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // String 결과를 리턴함
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    // UI를 업데이트를 하는동안 사용되는 메소드, 현재 thread sleep time을 알 수 있음
    @Override
    protected void onProgressUpdate(Integer... values) {
        mResultTextView.get().setText("Current sleep time: " + values[0] + " ms");
    }

    // doInBackground가 완료됐다면 자동으로 그 값이 onPostExecute로 들어감
    @Override
    protected void onPostExecute(String result) {
        mTextView.get().setText(result); // 넘겨받은 String을 표시하게 함
    }


}
