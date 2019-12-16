package volunteer.upay.com.upay.localDb;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.util.FetchUtils;
import volunteer.upay.com.upay.util.TaskUtilities;

public class CenterRepository {
    private String DB_NAME = "db_task";

    private UpayDatabase upayDatabase;

    public CenterRepository(Context context) {
        upayDatabase = FetchUtils.getDatabase(context, DB_NAME);
    }


    public void insertTask(final Centers note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.centersDao().insertCenter(note);
                return null;
            }
        }.execute();
    }

    public void updateTask(final Centers note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.centersDao().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final String id) {
        final Task<Centers> task = getTask(id);
        if (task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    upayDatabase.centersDao().deleteTask(task.getResult());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final Centers note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.centersDao().deleteTask(note);
                return null;
            }
        }.execute();
    }

    public Task<Centers> getTask(final String id) {
        return TaskUtilities.runOnBackgroundThread(new Callable<Centers>() {
            @Override
            public Centers call() throws Exception {
                return upayDatabase.centersDao().getTask(id);
            }
        });
    }

    public LiveData<List<Centers>> getTasks() {
        return upayDatabase.centersDao().fetchAllTasks();
    }
}
