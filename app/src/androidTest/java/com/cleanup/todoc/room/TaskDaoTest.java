package com.cleanup.todoc.room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.LiveDataTestUtil;
import com.cleanup.todoc.database.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

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
    public void insertAndGetTaskById() throws InterruptedException {

        // Given
        final Task expectedTask = new Task(5L, 3L, "Test Task", new Date().getTime());

        // When
        this.database.taskDao().insertTask(expectedTask);
        Task actualTask = LiveDataTestUtil.getValue(this.database.taskDao().getTaskById(expectedTask.getId()));

        // Then
        assertEquals(expectedTask.getId(), actualTask.getId());
        assertEquals(expectedTask.getProjectId(), actualTask.getProjectId());
        assertEquals(expectedTask.getName(), actualTask.getName());
        assertEquals(expectedTask.getCreationTimestamp(), actualTask.getCreationTimestamp());
    }

    @Test
    public void getTaskList() throws InterruptedException {

        // Given
        final Task[] expectedTasks = new Task[] {
                new Task(5L, 3L, "Test Task 5", new Date().getTime()),
                new Task(6L, 3L, "Test Task 6", new Date().getTime()),
                new Task(7L, 3L, "Test Task 7", new Date().getTime()),
        };

        // When
        this.database.taskDao().insertTask(expectedTasks[0]);
        this.database.taskDao().insertTask(expectedTasks[1]);
        this.database.taskDao().insertTask(expectedTasks[2]);
        List<Task> actualTasks = LiveDataTestUtil.getValue(this.database.taskDao().getTaskList());

        // Then
        boolean foundTask = false;
        for (int e = 0; e < expectedTasks.length; e++) {
            for (int a = 0; a < actualTasks.size(); a++) {
                if (expectedTasks[e].getId() == actualTasks.get(a).getId()) {
                    assertEquals(expectedTasks[e].getId(), actualTasks.get(a).getId());
                    assertEquals(expectedTasks[e].getProjectId(), actualTasks.get(a).getProjectId());
                    assertEquals(expectedTasks[e].getName(), actualTasks.get(a).getName());
                    assertEquals(expectedTasks[e].getCreationTimestamp(), actualTasks.get(a).getCreationTimestamp());
                    foundTask = true;
                }
            }
            assertTrue(foundTask);
            foundTask = false;
        }
    }
}
