package volunteer.upay.com.upay.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import volunteer.upay.com.upay.models.VolunteerLogModel;

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
