package memsa.ahmed.news.Sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by asmaa on 8/29/2017.
 */

public class NewsSyncIntentService extends IntentService {
    public NewsSyncIntentService() {
        super("NewsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NewsSyncTask.syncNewsOne(this);
        NewsSyncTask.syncNewsTwo(this);
        NewsSyncTask.syncNewsThree(this);
    }
}
