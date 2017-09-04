package memsa.ahmed.news.Sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by asmaa on 8/29/2017.
 */

public class NewsFireBaseJobService extends JobService {
    private AsyncTask<Void, Void, Void> newsTask;


    @Override
    public boolean onStartJob(final JobParameters job) {
        newsTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                NewsSyncTask.syncNewsOne(context);
                NewsSyncTask.syncNewsTwo(context);
                NewsSyncTask.syncNewsThree(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };

        newsTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (newsTask != null) {
            newsTask.cancel(true);
        }
        return true;

    }
}
