package com.cleanup.todoc.room;

import static org.junit.Assert.assertEquals;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
        final Project project = new Project(3L, "Projet test", 0xFFEADAD1);
        this.database.projectDao().insertProject(project);
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetTask() throws InterruptedException {

        // Given
        final Task expectedTask = new Task(5L, 3L, "Test Task", new Date().getTime());

        // When
        this.database.taskDao().insertTask(expectedTask);
        Task actualTask = this.database.taskDao().getTaskById(expectedTask.getId());
        // TODO Rajouter Live Data

        // Then
        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getProjectId(), actualTask.getProjectId());
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getCreationTimestamp(), actualTask.getCreationTimestamp());
    }

    // TODO Finir les test de toute les fonctionnalités
}
