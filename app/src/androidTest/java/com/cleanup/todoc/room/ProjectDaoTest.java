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

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private TodocDatabase database;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        database.close();
    }

    @Test
    public void insertAndGetProjectById() throws InterruptedException {

        // Given
        final Project expectedProject = new Project(5L, "Projet test", 0xFFEADAD1);

        // When
        this.database.projectDao().insertProject(expectedProject);
        Project actualProject = LiveDataTestUtil.getValue(this.database.projectDao().getProjectById(expectedProject.getId()));

        // Then
        assertEquals(expectedProject.getId(), actualProject.getId());
        assertEquals(expectedProject.getName(), actualProject.getName());
        assertEquals(expectedProject.getColor(), actualProject.getColor());
    }

    @Test
    public void insertProjectArrayAndGetAllProject() throws InterruptedException {

        // Given
        final Project[] expectedProjects = new Project[]{
                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                new Project(3L, "Projet Circus", 0xFFA3CED2),
        };

        // When
        this.database.projectDao().insertProjectArray(expectedProjects);
        Project[] actualProjects = LiveDataTestUtil.getValue(this.database.projectDao().getAllProjects());

        // Then
        boolean foundProject = false;
        for (Project expectedProject : expectedProjects) {
            for (Project actualProject : actualProjects) {
                if (expectedProject.getId() == actualProject.getId()) {
                    assertEquals(expectedProject.getId(), actualProject.getId());
                    assertEquals(expectedProject.getName(), actualProject.getName());
                    assertEquals(expectedProject.getColor(), actualProject.getColor());
                    foundProject = true;
                }
            }
            assertTrue(foundProject);
            foundProject = false;
        }
    }

}
