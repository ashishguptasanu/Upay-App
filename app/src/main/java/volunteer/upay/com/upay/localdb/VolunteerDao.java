package volunteer.upay.com.upay.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import volunteer.upay.com.upay.models.Volunteer;


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
