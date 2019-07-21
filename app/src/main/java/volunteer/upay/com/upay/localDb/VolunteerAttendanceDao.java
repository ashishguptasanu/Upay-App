package volunteer.upay.com.upay.localDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import volunteer.upay.com.upay.Models.VolunteerLogModel;

@Dao
public interface VolunteerAttendanceDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertVolunteerAttendance(VolunteerLogModel volunteerLogModel);


    @Query("SELECT * FROM VolunteerLogModel")
    LiveData<List<VolunteerLogModel>> fetchAllLogs();


    @Query("SELECT * FROM VolunteerLogModel  WHERE id =:id COLLATE NOCASE")
    VolunteerLogModel getTask(String id);


    @Update
    void updateTask(VolunteerLogModel volunteerLogModel);


    @Delete
    void deleteTask(VolunteerLogModel volunteerLogModel);
}
