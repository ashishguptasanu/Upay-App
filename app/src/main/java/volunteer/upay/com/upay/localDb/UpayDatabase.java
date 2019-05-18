package volunteer.upay.com.upay.localDb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import volunteer.upay.com.upay.Models.Centers;

@Database(entities = {Centers.class}, version = 1, exportSchema = false)
public abstract class UpayDatabase extends RoomDatabase {

    public abstract CentersDao centersDao();
}
