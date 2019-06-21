package volunteer.upay.com.upay.localDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import volunteer.upay.com.upay.Models.Volunteer;


@Dao
public interface VolunteerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCenter(Volunteer note);


    @Query("SELECT * FROM Volunteer  ORDER BY name desc")
    LiveData<List<Volunteer>> fetchAllTasks();


    @Query("SELECT * FROM Volunteer  WHERE email_id =:emailId COLLATE NOCASE")
    Volunteer getTask(String emailId);


    @Update
    void updateTask(Volunteer note);


    @Delete
    void deleteTask(Volunteer note);
}
