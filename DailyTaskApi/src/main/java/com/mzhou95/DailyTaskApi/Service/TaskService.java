package com.mzhou95.DailyTaskApi.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzhou95.DailyTaskApi.Model.Task;
import com.mzhou95.DailyTaskApi.Model.User;
import com.mzhou95.DailyTaskApi.Persistence.TaskEntity;
import com.mzhou95.DailyTaskApi.Persistence.TaskRepository;
import com.mzhou95.DailyTaskApi.Persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class TaskService {
    TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskEntity getTaskById(Integer id) {
        return taskRepository.getTaskEntityByTaskId(id);
    }

    public List<TaskEntity> getTasksById(Integer id) {
        List<TaskEntity> tasksByUser = taskRepository.findAllByUserId(id);
        return tasksByUser;
    }

    public Optional<TaskEntity> insertNewTask(HttpEntity<String> task) {
        Optional<TaskEntity> insertedTask = Optional.empty();

        Optional<Task> taskFromHttpBody = jsonToTaskModel(task.getBody());

        if(taskFromHttpBody.isPresent()) {
            Task extractTask = taskFromHttpBody.get();
            TaskEntity newTask = new TaskEntity(
                    extractTask.getUserId(),
                    extractTask.getTaskName(),
                    extractTask.getTaskDailyCount(),
                    extractTask.getTaskCompleteCount(),
                    extractTask.getTaskRestBonus(),
                    extractTask.getTaskSchedule()
            );
            TaskEntity returnedTask = taskRepository.save(newTask);
            insertedTask = Optional.of(returnedTask);
        }
        return insertedTask;
    }

    public Optional<TaskEntity> updateTask(Integer id, HttpEntity<String> task) {
        Optional<TaskEntity> updatedTask = Optional.empty();
        Optional<TaskEntity> oldTask = Optional.ofNullable(taskRepository.getTaskEntityByTaskId(id));

        if(oldTask.isEmpty()){
            return updatedTask;
        }
        Optional<Task> taskFromHttpBody = jsonToTaskModel(task.getBody());
        if(taskFromHttpBody.isPresent()){
            TaskEntity taskToUpdate = oldTask.get();
            if(taskFromHttpBody.get().getUserId() != oldTask.get().getUserId()) return updatedTask;

            Task newDetails = taskFromHttpBody.get();
            taskToUpdate.setTaskName(newDetails.getTaskName());
            taskToUpdate.setTaskSchedule(newDetails.getTaskSchedule());
            taskToUpdate.setTaskDailyCount(newDetails.getTaskDailyCount());
            taskToUpdate.setTaskCompleteCount(newDetails.getTaskCompleteCount());
            taskToUpdate.setTaskRestBonus(newDetails.getTaskRestBonus());

            TaskEntity update = taskRepository.save(taskToUpdate);
            updatedTask = Optional.of(update);
        }
        return updatedTask;
    }
    private Optional<Task> jsonToTaskModel(String jsonTask){
        ObjectMapper mapper = new ObjectMapper();
        Optional<Task> task = Optional.empty();
        try{
            Task mappedTask = mapper.readValue(jsonTask, Task.class);
            task = Optional.of(mappedTask);
        }catch (Exception e){
            e.printStackTrace();
        }
        return task;
    }

    public Optional<TaskEntity> deleteTaskId(Integer taskId, Integer userId) {
        Optional<TaskEntity> taskToDelete = Optional.ofNullable(taskRepository.getTaskEntityByTaskId(taskId));
        if(userId != taskToDelete.get().getUserId()){
            return Optional.empty();
        }
        if(taskToDelete.isPresent()){
            taskRepository.deleteById(taskId);
        }
        return taskToDelete;
    }
}
