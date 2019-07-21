package volunteer.upay.com.upay.localDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.Models.Volunteer;
import volunteer.upay.com.upay.Models.VolunteerLogModel;

@Database(entities = {Centers.class, Volunteer.class, VolunteerLogModel.class}, version = 3, exportSchema = false)
public abstract class UpayDatabase extends RoomDatabase {

    public abstract CentersDao centersDao();

    public abstract VolunteerDao volunteerDao();

    public abstract VolunteerAttendanceDao volunteerAttendanceDao();
}
