package volunteer.upay.com.upay.localdb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import volunteer.upay.com.upay.models.Centers;

@Dao
public interface CentersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertCenter(Centers note);


    @Query("SELECT * FROM Centers  ORDER BY center_name desc")
    LiveData<List<Centers>> fetchAllTasks();


    @Query("SELECT * FROM Centers  WHERE center_id =:centerId")
    Centers getTask(String centerId);


    @Update
    void updateTask(Centers note);


    @Delete
    void deleteTask(Centers note);

}
