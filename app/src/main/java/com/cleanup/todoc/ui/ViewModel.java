package com.cleanup.todoc.ui;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.ProjectDataRepository;
import com.cleanup.todoc.repository.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class ViewModel extends androidx.lifecycle.ViewModel {

    private final ProjectDataRepository projectDataRepository;
    private final TaskDataRepository taskDataRepository;
    private final Executor executor;

    public ViewModel(ProjectDataRepository projectDataRepository, TaskDataRepository taskDataRepository, Executor executor) {
        this.projectDataRepository = projectDataRepository;
        this.taskDataRepository = taskDataRepository;
        this.executor = executor;
    }

    public LiveData<Project[]> getAllProject() {
        return projectDataRepository.getAllProject();
    }

    public LiveData<List<Task>> getTaskList() {
        return taskDataRepository.getTaskList();
    }

    public void addTask(Task task) {
        executor.execute(() -> { taskDataRepository.addTask(task); });
    }

    public void deleteTask(Task task) {
        executor.execute( () -> taskDataRepository.deleteTask(task) );
    }
}
