package volunteer.upay.com.upay.localdb;

import androidx.lifecycle.LiveData;

import android.content.Context;

import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;
import volunteer.upay.com.upay.models.Volunteer;
import volunteer.upay.com.upay.util.FetchUtils;
import volunteer.upay.com.upay.util.TaskUtilities;

public class VolunteerRepository {

    private String DB_NAME = "db_task";

    private UpayDatabase upayDatabase;

    public VolunteerRepository(Context context) {
        upayDatabase = FetchUtils.getDatabase(context, DB_NAME);
    }


    public void insertTask(final Volunteer note) {
        TaskUtilities.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                upayDatabase.volunteerDao().insertCenter(note);
            }
        });
    }

    public void updateTask(final Volunteer note) {
        TaskUtilities.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                upayDatabase.volunteerDao().updateTask(note);
            }
        });
    }

    public void deleteTask(final String id) {
        final Task<Volunteer> task = getTask(id);
        if (task != null) {
            TaskUtilities.runOnBackgroundThread(new Runnable() {
                @Override
                public void run() {
                    upayDatabase.volunteerDao().deleteTask(task.getResult());
                }
            });
        }
    }

    public void deleteTask(final Volunteer note) {
        TaskUtilities.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {
                upayDatabase.volunteerDao().deleteTask(note);
            }
        });
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
