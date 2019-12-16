package volunteer.upay.com.upay.localDb;

import androidx.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import volunteer.upay.com.upay.Models.VolunteerLogModel;
import volunteer.upay.com.upay.util.FetchUtils;
import volunteer.upay.com.upay.util.TaskUtilities;

public class VolunteerAttendanceRepository {
    private String DB_NAME = "db_task";

    private UpayDatabase upayDatabase;

    public VolunteerAttendanceRepository(Context context) {
        upayDatabase = FetchUtils.getDatabase(context, DB_NAME);
    }


    public void insertTask(final VolunteerLogModel note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.volunteerAttendanceDao().insertVolunteerAttendance(note);
                return null;
            }
        }.execute();
    }

    public void updateTask(final VolunteerLogModel note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.volunteerAttendanceDao().updateTask(note);
                return null;
            }
        }.execute();
    }

    public void deleteTask(final String id) {
        final Task<VolunteerLogModel> task = getTask(id);
        if (task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    upayDatabase.volunteerAttendanceDao().deleteTask(task.getResult());
                    return null;
                }
            }.execute();
        }
    }

    public void deleteTask(final VolunteerLogModel note) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                upayDatabase.volunteerAttendanceDao().deleteTask(note);
                return null;
            }
        }.execute();
    }

    public Task<VolunteerLogModel> getTask(final String emailId) {
        return TaskUtilities.runOnBackgroundThread(new Callable<VolunteerLogModel>() {
            @Override
            public VolunteerLogModel call() throws Exception {
                return upayDatabase.volunteerAttendanceDao().getTask(emailId);
            }
        });
    }

    public LiveData<List<VolunteerLogModel>> getTasks() {
        return upayDatabase.volunteerAttendanceDao().fetchAllLogs();
    }
}
