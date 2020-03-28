package volunteer.upay.com.upay.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import volunteer.upay.com.upay.models.Centers;
import volunteer.upay.com.upay.models.Volunteer;
import volunteer.upay.com.upay.models.VolunteerLogModel;

@Database(entities = {Centers.class, Volunteer.class, VolunteerLogModel.class}, version = 4, exportSchema = false)
public abstract class UpayDatabase extends RoomDatabase {

    public abstract CentersDao centersDao();

    public abstract VolunteerDao volunteerDao();

    public abstract VolunteerAttendanceDao volunteerAttendanceDao();
}
