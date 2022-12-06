package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long insertTask(Task task);

    @Query("SELECT * FROM Task WHERE id = :id")
    Task getTaskById(Long id);

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTaskList();

    @Delete
    int deleteTask(Task task);
}
