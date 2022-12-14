package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public LiveData<List<Task>> getTaskList() {
        return taskDao.getTaskList();
    }

    public long addTask(Task task) {
        return taskDao.insertTask(task);
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }
}
