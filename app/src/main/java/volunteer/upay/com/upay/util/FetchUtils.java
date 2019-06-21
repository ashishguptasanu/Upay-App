package volunteer.upay.com.upay.util;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import bolts.Task;
import volunteer.upay.com.upay.Models.Centers;
import volunteer.upay.com.upay.Models.Volunteer;
import volunteer.upay.com.upay.localDb.CenterRepository;
import volunteer.upay.com.upay.localDb.UpayDatabase;
import volunteer.upay.com.upay.localDb.VolunteerRepository;

public class FetchUtils {

    public static Task<Centers> getCenterNameFromId(@NonNull Context context, @NonNull String centerId) {
        CenterRepository centerRepository = new CenterRepository(context);
        return centerRepository.getTask(centerId);
    }

    public static Task<Volunteer> getVolunteerName(@NonNull Context context, @NonNull String emailId) {
        VolunteerRepository volunteerRepository = new VolunteerRepository(context);
        return volunteerRepository.getTask(emailId);
    }

    public static UpayDatabase getDatabase(Context context, String db_name) {
        Migration migration_1_2 = new Migration(1, 2) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("CREATE TABLE IF NOT EXISTS `Volunteer` (`id` TEXT NOT NULL, `center_name` TEXT, `center_id` TEXT, `zone_name` TEXT, `zone_id` TEXT, `upay_id` TEXT, `email_id` TEXT, `phone` TEXT, `password` TEXT, `admin_access` TEXT, `name` TEXT, `added_by` TEXT, `photoUrl` TEXT, PRIMARY KEY(`id`))");
            }
        };
        return Room.databaseBuilder(context, UpayDatabase.class, db_name)
                .addMigrations(migration_1_2)
                .build();
    }
}
