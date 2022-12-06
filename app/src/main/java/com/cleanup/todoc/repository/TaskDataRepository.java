package com.cleanup.todoc.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDataRepository {

    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public long addTask(Task task) {
        return taskDao.insertTask(task);
    }

    public LiveData<List<Task>> getTaskList() {
        return taskDao.getTaskList();
    }

    public void deleteTask(Task task) {
        taskDao.deleteTask(task);
    }
}
