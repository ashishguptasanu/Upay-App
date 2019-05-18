package volunteer.upay.com.upay.localDb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import volunteer.upay.com.upay.Models.Centers;

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
