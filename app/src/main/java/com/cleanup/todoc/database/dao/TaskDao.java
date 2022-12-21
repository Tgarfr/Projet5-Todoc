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

    @Query("SELECT * FROM Task")
    LiveData<List<Task>> getTaskList();

    @Query("SELECT * FROM Task WHERE id = :id")
    LiveData<Task> getTaskById(Long id);

    @Insert
    long insertTask(Task task);

    @Delete
    void deleteTask(Task task);
}
