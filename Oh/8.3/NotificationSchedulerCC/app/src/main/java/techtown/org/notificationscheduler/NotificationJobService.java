package techtown.org.notificationscheduler;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;

import androidx.core.app.NotificationCompat;

/*
개발자가 정의하는 작업을 스케줄링 해주는 JobService 클래스, 백그라운드에서 작업을 하므로 효율도 좋음
 */

public class NotificationJobService extends JobService {
    Context mContext;
    JobParameters jobParameters;

    public NotificationJobService(Context mContext) {
        this.mContext = mContext;
    }


    // 예약된 작업을 실행함
    @Override
    public boolean onStartJob(JobParameters params) {
        jobParameters = params;
        new MyAsyncTask().execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        // 만약 job이 실패했다면 job을 reschedule하기 위해 true로 설정함
        return true;
    }

    // 5초 sleep을 걸고 토스트 메시지를 띄움, 이러한 백그라운드 작업을 하는데 있어서 AsyncTask 사용
    private class MyAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(jobParameters!=null) {
                // 작업이 끝남을 알리는 작업을 함
                jobFinished(jobParameters, false);
            }
        }
    }
}
