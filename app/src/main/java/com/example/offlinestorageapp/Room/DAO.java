package com.example.offlinestorageapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO {
    @Insert
    void insertData(Task task);

    @Delete
    void deleteData(Task task);

    @Update
    void updateData(Task task);

    @Query("select * from task_details")
    List<Task> readData();
}
