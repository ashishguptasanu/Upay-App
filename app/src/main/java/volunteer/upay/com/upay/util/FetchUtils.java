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
        Migration migration_3_4 = new Migration(3, 4) {
            @Override
            public void migrate(@NonNull SupportSQLiteDatabase database) {
                database.execSQL("DROP TABLE  IF EXISTS `VolunteerLogModel`");
                database.execSQL("CREATE TABLE IF NOT EXISTS `VolunteerLogModel` (`id` TEXT NOT NULL, `volunteer_id` TEXT, `center_id` TEXT, `attendance_status` TEXT, `timestmp` TEXT, `in_time` TEXT, `out_time` TEXT, `class_taught` TEXT, `subject` TEXT, `work_done` TEXT, `thought_of_day` TEXT, `no_of_students` TEXT,  `is_uploaded` INTEGER NOT NULL ,  PRIMARY KEY(`id`))");
            }
        };
        return Room.databaseBuilder(context, UpayDatabase.class, db_name)
                .addMigrations(migration_1_2)
                .addMigrations(migration_3_4)
                .build();
    }
}
