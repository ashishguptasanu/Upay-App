package volunteer.upay.com.upay.localDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import volunteer.upay.com.upay.Models.Volunteer;
import volunteer.upay.com.upay.util.FetchUtils;
import volunteer.upay.com.upay.util.TaskUtilities;

public class VolunteerRepository {

    private String DB_NAME = "db_task";

    private UpayDatabase upayDatabase;

    public VolunteerRepository(Context context) {
        upayDatabase = FetchUtils.getDatabase(context, DB_NAME);
    }


    public void insertTask(final Volunteer note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.volunteerDao().insertCenter(note);
                return null;
            }
        }.execute();
    }

    public void updateTask(final Volunteer note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.volunteerDao().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final String id) {
        final Task<Volunteer> task = getTask(id);
        if (task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    upayDatabase.volunteerDao().deleteTask(task.getResult());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final Volunteer note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.volunteerDao().deleteTask(note);
                return null;
            }
        }.execute();
    }

    public Task<Volunteer> getTask(final String emailId) {
        return TaskUtilities.runOnBackgroundThread(new Callable<Volunteer>() {
            @Override
            public Volunteer call() throws Exception {
                return upayDatabase.volunteerDao().getTask(emailId);
            }
        });
    }

    public LiveData<List<Volunteer>> getTasks() {
        return upayDatabase.volunteerDao().fetchAllTasks();
    }
}
