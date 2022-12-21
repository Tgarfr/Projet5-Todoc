package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;


@Dao
public interface ProjectDao {

    @Query("SELECT * FROM Project")
    LiveData<Project[]> getAllProjects();

    @Query("SELECT * FROM Project WHERE id = :id")
    LiveData<Project> getProjectById(Long id);

    @Insert
    long insertProject(Project project);

    @Insert
    void insertProjectArray(Project[] projectArray);
}
