package com.mzhou95.DailyTaskApi.Controller;

import com.mzhou95.DailyTaskApi.Persistence.TaskEntity;
import com.mzhou95.DailyTaskApi.Persistence.UserEntity;
import com.mzhou95.DailyTaskApi.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/v1/task")
    public TaskEntity getTaskById(@RequestParam Integer id){
        return taskService.getTaskById(id);
    }

    @GetMapping("/v1/tasks")
    public ResponseEntity<?> getAllTasksById(@RequestParam Integer id){
        List<TaskEntity> tasks = taskService.getTasksById(id);
        HttpStatus status = HttpStatus.CONFLICT;
        if(!tasks.isEmpty()){
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(tasks, status);
    }

    @PostMapping("/v1/create-task")
    public ResponseEntity<?> addTask(HttpEntity<String> httpEntity){
        Optional<TaskEntity> insertionSuccess = taskService.insertNewTask(httpEntity);
        Integer taskId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if (insertionSuccess.isPresent()){
            taskId = insertionSuccess.get().getUserId();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(taskId, status);
    }

    @PutMapping("/v1/update-task")
    public ResponseEntity<?> changePassword(@RequestParam Integer id, HttpEntity<String> httpEntity) {
        Optional<TaskEntity> insertionSuccess = taskService.updateTask(id, httpEntity);
        TaskEntity updatedTask = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if (insertionSuccess.isPresent()) {
            updatedTask = insertionSuccess.get();
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(updatedTask, status);
    }

    @DeleteMapping("/v1/delete-task")
    public ResponseEntity<?> deleteUser(@RequestParam Integer taskId, @RequestParam Integer userId){
        Optional<TaskEntity> deletionSuccess = taskService.deleteTaskId(taskId, userId);
        Integer deletedTaskId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if(deletionSuccess.isPresent()){
            deletedTaskId = deletionSuccess.get().getUserId();
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(deletedTaskId, status);
    }
}
